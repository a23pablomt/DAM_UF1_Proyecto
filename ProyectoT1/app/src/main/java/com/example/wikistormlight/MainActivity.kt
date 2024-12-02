package com.example.wikistormlight

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wikistormlight.model.controller.Controller
import com.example.wikistormlight.model.dataclasses.Character
import com.example.wikistormlight.model.deserializators.CharacterListCreator
import com.example.wikistormlight.ui.theme.WikiStormlightTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.resources.configuration.uiMode = Configuration.UI_MODE_NIGHT_NO
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
             MyApp(navController)
        }
    }
}

@Composable
fun MyApp(navController: NavHostController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "start/WikiStormlight") {
        composable("start/{name}") { backStackEntry ->
            WikiStormlightTheme {
                Scaffold( modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding() ) { innerPadding ->
                    backStackEntry.arguments?.getString("name")?.let {
                        Greeting(
                            it,
                            modifier = Modifier.padding(innerPadding),
                            navController,
                            drawerState,
                            scope
                        )
                    }
                }
            }
        }
        composable("select/{name}/{tipo}") { backStackEntry ->
            WikiStormlightTheme {
                Scaffold( modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding() ) { innerPadding ->
                    backStackEntry.arguments?.getString("name")?.let {
                        backStackEntry.arguments!!.getString("tipo")?.toInt()?.let { it1 ->
                            SelectorDePersonaje(
                                it,
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                drawerState,
                                scope,
                                it1
                            )
                        }
                    }
                }
            }
        }
        composable("datos/{personaje}") { backStackEntry ->
            WikiStormlightTheme {
                Scaffold( modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding() ) { innerPadding ->
                    backStackEntry.arguments?.getString("personaje")?.let {
                        PersonajeWiki(
                            it,
                            modifier = Modifier.padding(innerPadding),
                            navController,
                            drawerState,
                            scope
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  Greeting(named: String, modifier: Modifier = Modifier, navController: NavController, drawerState: DrawerState, scope: CoroutineScope) {

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFF78A0C8)) {
                Image(painter = painterResource(R.drawable.shallan),
                    contentDescription = ("Str"),
                    modifier = Modifier.fillMaxWidth())
                HorizontalDivider(modifier = Modifier
                    .padding(5.dp)
                    .offset(0.dp, (-2).dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null) },
                    label = { Text(text = "Personajes", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Personajes/1") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.AddCircle, contentDescription = null) },
                    label = { Text(text = "Grupos", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Grupos/3") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))

                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = null) },
                    label = { Text(text = "Lugares", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Lugares") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null) },
                    label = { Text(text = "Magia", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Magia") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))

            }
        }
    ) {
        Surface(color = Color(0xFFD9D9D9)){
            var mDisplayMenu by remember { mutableStateOf(false) }

            val mContext = LocalContext.current

            val controller = Controller.getInstance(mContext)
            val nombres = listOf(controller.getCharacter("Abrial") ?: Character("Unknown", "Unknown", "Uknown","Uknown", null,  ""),
                controller.getCharacter("Shallan Davar") ?: Character("Unknown", "Unknown", "Unknown","Uknown", null, ""))

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Open Navigation Drawer")
                    }
                },
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = named,
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF78A0C8)),
                actions = {
                    IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                        Icon(Icons.Default.MoreVert, "")
                    }

                    DropdownMenu(
                        expanded = mDisplayMenu,
                        onDismissRequest = { mDisplayMenu = false }
                    ) {}
                }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
                Image(
                    painter = painterResource(R.drawable.choose_order),
                    contentDescription = ("Str"),
                    modifier = Modifier
                        .padding(100.dp)
                        .width(300.dp)
                        .height(200.dp)
                )
                var query by remember { mutableStateOf("") }
                var isActive by remember { mutableStateOf(false) } // Estado para gestionar la activación de la SearchBar
                SearchBar(modifier = Modifier
                    .width(300.dp)
                    .offset(0.dp, (-120).dp),
                    query = query,
                    onQueryChange = { newQuery -> query = newQuery }, // Actualizar query cuando cambia
                    onSearch = {
                        navController.navigate("select/$query/0")
                    },
                    active = false, // Establecer activo para permitir la escritura
                    onActiveChange = { isActive = it }, // Cambiar el estado activo cuando cambie
                    placeholder = { Text(text = "Search...") },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                ) {}
                Row(modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
                    .offset(0.dp, (-50).dp)){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        listOf(items(1) {
                            Box(
                                modifier = Modifier
                                    .height(270.dp)
                                    .clickable {
                                        navController.navigate("datos/${nombres[0].name}")
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(120.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.White)
                                        .align(Alignment.TopCenter)
                                ) {
                                    Image(
                                        painter = BitmapPainter(controller.readAssetImg(nombres[0].name)),
                                        contentDescription = "Image",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(20.dp))
                                            .border(5.dp, Color.White, RoundedCornerShape(20.dp)),
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.White)
                                        .align(Alignment.Center)
                                ) {
                                    Text(
                                        nombres[0].name,
                                        modifier = Modifier.align(Alignment.Center),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        },
                            items(1) {
                                Box(
                                    modifier = Modifier
                                        .height(270.dp)
                                        .clickable {
                                            navController.navigate("datos/${nombres[1].name}")
                                        }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(120.dp)
                                            .height(120.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(Color.White)
                                            .align(Alignment.TopCenter)

                                    ) {
                                        Image(
                                            painter = BitmapPainter(controller.readAssetImg(nombres[1].name)),
                                            contentDescription = "Image",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(20.dp))
                                                .border(
                                                    5.dp,
                                                    Color.White,
                                                    RoundedCornerShape(20.dp)
                                                ),
                                            contentScale = ContentScale.Crop,
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .width(120.dp)
                                            .height(20.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(Color.White)
                                            .align(Alignment.Center)
                                    ) {
                                        Text(
                                            nombres[1].name,
                                            modifier = Modifier.align(Alignment.Center),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorDePersonaje(name: String, modifier: Modifier = Modifier, navController: NavController, drawerState: DrawerState, scope: CoroutineScope, tipoPantalla: Int) {

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFF78A0C8)) {
                Image(painter = painterResource(R.drawable.shallan),
                    contentDescription = ("Str"),
                    modifier = Modifier.fillMaxWidth())
                HorizontalDivider(modifier = Modifier
                    .padding(5.dp)
                    .offset(0.dp, (-2).dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null) },
                    label = { Text(text = "Personajes", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Personajes/1") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.AddCircle, contentDescription = null) },
                    label = { Text(text = "Grupos", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Grupos") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))

                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = null) },
                    label = { Text(text = "Lugares", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Lugares") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null) },
                    label = { Text(text = "Magia", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Magia") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))

            }
        }
    ) {Surface(color = Color(0xFFD9D9D9)) {
        var mDisplayMenu by remember { mutableStateOf(false) }

        val mContext = LocalContext.current

        val controller = Controller.getInstance(mContext)

        

        val lista = CharacterListCreator().createCharacterList(controller.readAssetFile("characters"))
        val nombres = mutableListOf<Character>()
        when (tipoPantalla) {
            0 -> {
                for (i in lista){
                    val pj = controller.getCharacter(i) ?: Character("None", "None", "None","Uknown", null, "")
                    if ((pj).name.lowercase().contains(name.lowercase())){
                        nombres.add(pj)
                    }
                }
            }
            1 -> {
                for (i in lista){
                    val pj = controller.getCharacter(i) ?: Character("None", "None", "None","Uknown", null, "")
                    if ((pj).img != "null"){
                        nombres.add(pj)
                    }
                }
            }
            else -> {
                for (i in lista){
                    val pj = controller.getCharacter(i) ?: Character("None", "None", "None","Uknown", null, "")
                    nombres.add(pj)
                }
            }
        }


        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        }
                    }
                }) {
                    Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Open Navigation Drawer")
                }
            },
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = (if(tipoPantalla == 0){"\""+name+"\""} else {name}),
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF78A0C8)),
            actions = {
                IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                    Icon(Icons.Default.MoreVert, "")
                }

                DropdownMenu(
                    expanded = mDisplayMenu,
                    onDismissRequest = { mDisplayMenu = false }
                ) {}
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(16.dp, 0.dp)
            ) {
                items(nombres.size) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp, 30.dp)
                            .clickable {
                                navController.navigate("datos/${nombres[index].name}")
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .align(Alignment.TopCenter)
                        ) {
                            Image(
                                painter = BitmapPainter(controller.readAssetImg(nombres[index].name)),
                                contentDescription = "Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(5.dp, Color.White, RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .height(20.dp)
                                .offset(0.dp, 75.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .align(Alignment.Center)
                        ) {
                            Text(
                                nombres[index].name,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonajeWiki(name: String, modifier: Modifier = Modifier, navController: NavController, drawerState: DrawerState, scope: CoroutineScope) {

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFF78A0C8)) {
                Image(painter = painterResource(R.drawable.shallan),
                    contentDescription = ("Str"),
                    modifier = Modifier.fillMaxWidth())
                HorizontalDivider(modifier = Modifier
                    .padding(5.dp)
                    .offset(0.dp, (-2).dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null) },
                    label = { Text(text = "Personajes", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Personajes/1") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.AddCircle, contentDescription = null) },
                    label = { Text(text = "Grupos", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Grupos") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))

                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = null) },
                    label = { Text(text = "Lugares", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Lugares") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null) },
                    label = { Text(text = "Magia", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { navController.navigate("select/Magia") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))

            }
        }
    ) {Surface(color = Color(0xFFD9D9D9)) {
        var mDisplayMenu by remember { mutableStateOf(false) }

        val mContext = LocalContext.current

        val controller = Controller.getInstance(mContext)
        val nombre = controller.getCharacter(name) ?: Character("Unknown", "Unknown", "Unknown","Uknown", null, "")

        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Open Navigation Drawer"
                    )
                }
            },
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = name,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF78A0C8)),
            actions = {
                IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                    Icon(Icons.Default.MoreVert, "")
                }

                DropdownMenu(
                    expanded = mDisplayMenu,
                    onDismissRequest = { mDisplayMenu = false }
                ) {}
            }
        )
        Box(modifier = Modifier.padding(0.dp, 64.dp, 0.dp, 0.dp)){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(0.dp, 20.dp)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)) {
                    Column(modifier = Modifier
                        .size(200.dp)
                        .padding(10.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                        ) {
                            Image(
                                painter = BitmapPainter(controller.readAssetImg(nombre.name)),
                                contentDescription = "Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(5.dp, Color.White, RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(20.dp)
                                .offset(0.dp, 35.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .align(Alignment.Start)
                        ) {
                            Text(
                                nombre.name,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(20.dp)
                                .offset(0.dp, 50.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.LightGray)
                                .align(Alignment.Start)
                        ) {
                            Text(
                                "Etnithity: "+nombre.etnithity,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(20.dp)
                                .offset(0.dp, 45.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.LightGray)
                                .align(Alignment.Start)
                        ) {
                            Text(
                                "Nationality: "+nombre.nationality,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(20.dp)
                                .offset(0.dp, 40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.LightGray)
                                .align(Alignment.Start)
                        ) {
                            Text(
                                "Gender: "+nombre.gender,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                    ) { nombre.description?.let { Text(it, modifier = Modifier.padding(15.dp)) } }
                }
            }
        }
    }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WikiStormlightTheme {
        Greeting(
            "Kabsal",
            navController = rememberNavController(), drawerState = rememberDrawerState(initialValue = DrawerValue.Closed), scope = rememberCoroutineScope()
        )
    }
}