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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WikiStormlightTheme {
                Scaffold( modifier = Modifier.fillMaxSize().systemBarsPadding() ) { innerPadding ->
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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Surface(color = Color(0xFFD9D9D9)){
        var mDisplayMenu by remember { mutableStateOf(false) }

        val mContext = LocalContext.current
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
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(R.drawable.choose_order),
                contentDescription = ("Str"),
                modifier = Modifier
                    .padding(100.dp)
                    .width(300.dp)
                    .height(200.dp)
            )
            SearchBar(modifier = Modifier
                .width(300.dp)
                .offset(0.dp, (-120).dp),
                query = "",
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = {},
                placeholder = {
                    Text(text = "Buscar...")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }) {}
            Row(modifier = Modifier.width(300.dp).height(200.dp).offset(0.dp, (-50).dp)){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(16.dp)
                ) {
                    listOf(items(1) {
                        Box(
                            modifier = Modifier
                                .height(270.dp)
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
                                    painter = painterResource(id = R.drawable.shallan),
                                    contentDescription = "Image",
                                    modifier = Modifier.fillMaxHeight()
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
                                    "Sigzil",
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
                                    painter = painterResource(id = R.drawable.shallan),
                                    contentDescription = "Image",
                                    modifier = Modifier.fillMaxHeight()
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
                                    "Kaladin",
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
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFF78A0C8)) {
                Image(painter = painterResource(R.drawable.shallan),
                    contentDescription = ("Str"),
                    modifier = Modifier.fillMaxWidth())
                HorizontalDivider(modifier = Modifier.padding(5.dp).offset(0.dp, (-2).dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null) },
                    label = { Text(text = "Personajes", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { /*TODO*/ },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.AddCircle, contentDescription = null) },
                    label = { Text(text = "Grupos", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { /*TODO*/ },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))

                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = null) },
                    label = { Text(text = "Lugares", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { /*TODO*/ },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null) },
                    label = { Text(text = "Magia", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = { /*TODO*/ },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF78A0C8))
                )
                HorizontalDivider(modifier = Modifier.padding(5.dp), color = Color(0xFF141414))

            }
        }
    ) {}
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WikiStormlightTheme {
        Greeting("Android")
    }
}