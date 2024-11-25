package com.example.wikistormlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wikistormlight.ui.theme.WikiStormlightTheme
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WikiStormlightTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Surface(color = Color(0xFFD9D9D9), modifier = Modifier.paddingFromBaseline(0.dp, 200.dp)){
        // Create a boolean variable
        // to store the display menu state
        var mDisplayMenu by remember { mutableStateOf(false) }

        // fetching local context
        val mContext = LocalContext.current

        // Creating a Top bar
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
                }
            },
            title = {Text("El Archivo de las Tormentas", color = Color.White)} ,
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF78A0C8)),
            actions = {
                // Creating Icon button for dropdown menu
                IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                    Icon(Icons.Default.MoreVert, "")
                }

                // Creating a dropdown menu
                DropdownMenu(
                    expanded = mDisplayMenu,
                    onDismissRequest = { mDisplayMenu = false }
                ) {}
            }
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
            Image(
                painter = painterResource(R.drawable.choose_order),
                contentDescription = ("Str"),
                modifier = Modifier.padding(100.dp)
                    .width(300.dp)
                    .height(200.dp)
            )
            SearchBar(modifier = Modifier.width(300.dp).offset(0.dp, (-120).dp),
                query = "Buscar...",
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = {},
                placeholder = {
                    Text(text = "Enter your query")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }) {}
            Row(modifier = Modifier.width(300.dp)){
                Box(modifier = Modifier.height(230.dp)){
                    Box(modifier = Modifier.width(100.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .align(Alignment.TopStart)){
                        Image(
                            painter = painterResource(R.drawable.choose_order),
                            contentDescription = ("Str"),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Box(modifier = Modifier.width(100.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .align(Alignment.CenterEnd)){
                        Text("Sigzil", modifier = Modifier.align(Alignment.Center), fontSize = 13.sp)
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
        Greeting("Android")
    }
}