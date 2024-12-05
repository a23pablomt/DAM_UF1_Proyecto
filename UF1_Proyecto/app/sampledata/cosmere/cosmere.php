<?php

function activateErrorHandler() {
  set_error_handler(function($errno, $errstr, $errfile, $errline ) {
    throw new ErrorException($errstr, 0, $errno, $errfile, $errline);
  });
}

function deactivateErrorHandler() {
  restore_error_handler();
}

// Web scrapper PHP for the characters in the web https://stormlightarchive.fandom.com/wiki/
// The character list will be stored in characters.json
// and each character information will be stored in a file named as the character name with json extension

function CreateContextArray() {
  /*
    Crea e devolve un "ArrayContext" gen├®rico
  */
  $contextArray = array(
    'http'=>array(
      'protocol_version'=>'1.1',
      'method'=>"GET",
//      'proxy' => "tcp://127.0.0.1:8080",
      'header'=>"Accept-Language: es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3\r\n" .
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" .
                "Connection: Close\r\n" .
                "Cache-Control: no-cache,private\r\n" .
                "Pragma: no-cache\r\n" .
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:132.0) Gecko/20100101 Firefox/132.0\r\n",
      'max_redirects' => 1,
      'follow_location' => false
    )
  );
  if (file_exists(__DIR__ . "/" . "proxy.conf.php")) {
    include "proxy.conf.php";
  }
  return $contextArray;
}

function scrapeMainPage($url) {
  $letter='A';
  $characterList = [];
  while (strlen($letter)==1) {
    if (file_exists("cache/".$letter)) {
      $html = file_get_contents("cache/".$letter);
      print "Local letter \"$letter\"\r\n";
    } else {
      print "Online letter \"$letter\"\r\n";
      sleep(10);
      $html = file_get_contents($url.$letter);//,false,stream_context_create(CreateContextArray()));
      file_put_contents("cache/".$letter, $html);
    }

    $dom = new DOMDocument();
    deactivateErrorHandler();
    @$dom->loadHTML($html);
    activateErrorHandler();
    $xpath = new DOMXPath($dom);
    $characters = $xpath->query('.//li[@class="category-page__member"]/a');
    print "Found " . $characters->length . " characters for letter \"$letter\"\r\n";
    foreach ($characters as $character) {
      /** @var DOMElement $character*/
      $newItem = ["characterName" => $character->textContent, "characterURI" => $character->getAttribute('href')];
      if (strpos($newItem['characterURI'],":")===false) {
        $newItem['characterURI'] = "https://stormlightarchive.fandom.com".$newItem['characterURI'];
        $characterList[] = $newItem;
      }
    }
    if ($letter==='Z') {
      break;
    }
    $letter++;
    print "Next letter \"$letter\"\r\n";
  }
  file_put_contents('output/'.'characters.json', json_encode($characterList,JSON_PRETTY_PRINT));
  return $characterList;
}

function getCharacterInfoBlockData($xpath,$characterInfoNodes) {
  $characterInfoBlock =  $xpath->query('.//tr[@class="infoboxrow2"]',$characterInfoNodes);
  $characterInfo = [];
  foreach ($characterInfoBlock as $infoBlock) {
    $key = trim($infoBlock->childNodes[0]->textContent);
    $value = trim($infoBlock->childNodes[2]->textContent);
    $characterInfo[$key] = $value;
  }
  return $characterInfo;
}

function fixDescription($description) {
  $description = preg_replace('/\[\d+\]/', '', $description);
  return $description;
}

function scrapeEachCharacter($url,$characterName) {
  if (file_exists("cache/".$characterName.".character")) {
    $html = file_get_contents("cache/".$characterName.".character");
    print "Local character \"$characterName\"\r\n";
  } else {
    print "Online character \"$characterName\" \"$url\"\r\n";
    sleep(5);
    $html = file_get_contents($url);//,false,stream_context_create(CreateContextArray()));
    file_put_contents("cache/".$characterName.".character", $html);
  }
  $characterInfo = [];
  $characterInfo['characterName'] = $characterName;

  $html = file_get_contents($url);
  $dom = new DOMDocument();
  deactivateErrorHandler();
  @$dom->loadHTML($html);
  activateErrorHandler();
  $xpath = new DOMXPath($dom);
  // Get the character information
  $characterInfoBlock = $xpath->query('.//table[@class="infobox infoboxbody"]');
  $characterInfoData = getCharacterInfoBlockData($xpath,$characterInfoBlock[0]);
  $characterInfo['characterInfo'] = $characterInfoData;

  // Get the character image  
  $characterImageNode = $xpath->query('.//table[@class="infobox infoboxbody"]//td[@class="infoboximage"]//a//img');
  if ($characterImageNode->length==0) {
    $characterImageURL=null;
  } else {
    $characterImageURL = $characterImageNode[0]->getAttribute('data-src');
    if ($characterImageURL==="" || $characterImageURL==null) {
      $characterImageURL = $characterImageNode[0]->getAttribute('src');
    }
    if ($characterImageURL==="") {
      $characterImageURL=null;
    }
  }
  $characterInfo['characterImage'] = $characterImageURL;

  // Get the character description
  $characterDescription = $xpath->query('.//div[@class="mw-parser-output"]/*');
  $characterInfo['characterDescription'] = "";
  $firstParagraph = false;
  foreach ($characterDescription as $description) {
//    print "NodeName: " . $description->nodeName . "\r\n";
    if (($description->nodeName!=='p') and ($firstParagraph===true)) {
//      print "exit\r\n";
      break;
    } else {
//      print "continue\r\n";
    }
    if ($description->nodeName==='p') {
      $firstParagraph = true;
      if ($characterInfo["characterDescription"]!=="") {
        $characterInfo["characterDescription"] = $characterInfo["characterDescription"] . "\r\n";
      }
      $characterInfo['characterDescription'] = $characterInfo["characterDescription"] . fixDescription(trim($description->textContent));
    }
  }

  // remove invalid filename characters in the characterName
  //$characterName = preg_replace('/[^A-Za-z0-9\-]/', '', $characterName);
  file_put_contents('output/' . $characterName.'.json', json_encode($characterInfo,JSON_PRETTY_PRINT));
  return $characterInfo;
}

$characterList=scrapeMainPage('https://stormlightarchive.fandom.com/wiki/Category:Characters?from=');
print "Found " . count($characterList) . " characters\r\n";

foreach ($characterList as $character) {
  $character=scrapeEachCharacter($character['characterURI'],$character['characterName']);
  if ($character['characterImage']!==null) {
    if (!file_exists('images/".$character["characterName"].".jpg')) {
      $image = file_get_contents($character['characterImage']);
      file_put_contents('images/' . $character['characterName'].'.jpg', $image);
    }
  }
//  var_dump($character);
//  print "waiting 5 seconds\r\n";
//  sleep(5);
}
