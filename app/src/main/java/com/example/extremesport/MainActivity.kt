package com.example.extremesport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.extremesport.screen.*
import com.example.extremesport.ui.theme.ExtremeSportTheme
import com.example.extremesport.view.ESViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExtremeSportTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

/*
    Holds the function for a screen, makes it easier to display screens.
    The screen names still will have to be added to the enum class at the end,
    and the both the screens list and the enum class needs to be in the same order.
*/
data class Screen(
    val DisplayScreen: @Composable (PaddingValues?) -> Unit
)

@Composable
fun App() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val viewModel = ESViewModel()

    val screenNames = Screens.values().map { it.name }
    val screens = listOf(
        Screen {
            LoadingScreen(
                navController = navController,
                loadingFunction = { loadAPIs(viewModel) }
            )
        },
        Screen { innerPaddingValues ->
            MainScreen(viewModel = viewModel, innerPadding = innerPaddingValues!!)
        },
        Screen { SettingsScreen(viewModel) },
        Screen { ArkivScreen(viewModel) },
        Screen { FavorittScreen(viewModel) },
        Screen { ReportScreen(viewModel) },
        Screen { OmOssScreen(viewModel) },
    )

    NavHost(
        navController = navController,
        startDestination = Screens.LoadingScreen.name
    ) {

        screens.zip(screenNames) {screen, name ->
            composable(name) {
                if (name == "LoadingScreen") {
                    screen.DisplayScreen(null)
                } else {
                    MainScaffold(
                        navController = navController,
                        scaffoldState = scaffoldState,
                        coroutineScope = coroutineScope
                    ) {
                        screen.DisplayScreen(it)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScaffold(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { DrawerMenu(navController, scaffoldState, coroutineScope) },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        bottomBar = {
            BottomBar(navController = navController, scaffoldState = scaffoldState, coroutineScope = coroutineScope)
        },
        content = content
    )
}


@Composable
fun BottomBar(navController: NavHostController, scaffoldState: ScaffoldState, coroutineScope: CoroutineScope){
    var selected by remember { mutableStateOf("main")}

    BottomNavigation(
        modifier = Modifier
            .height(65.dp),
        backgroundColor = "#1C6EAE".color,
    )
    {
        BottomNavigationItem(
            selected = selected == "hamburger",
            onClick = {
                coroutineScope.launch { scaffoldState.drawerState.open()}
                selected = "hamburger"
            },
            unselectedContentColor = Color.LightGray,
            selectedContentColor = Color.White,
            icon = {
                Icon(
                    painterResource(id = R.drawable.baseline_menu_24_white),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                )
            },
        )
        BottomNavigationItem(
            selected = selected == "main",
            onClick =  {
                navController.navigate(Screens.MainScreen.name) {popUpTo(Screens.MainScreen.name)}
                selected = "main"
            },
            unselectedContentColor = Color.LightGray,
            selectedContentColor = Color.White,
            icon = {
                Icon(
                    painterResource(id =
                    if (selected == "main"){
                        R.drawable.baseline_place_24
                    }else{
                        R.drawable.outline_place_24 }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        )
        BottomNavigationItem(
            selected = selected == "settings",
            onClick =  {
                navController.navigate(Screens.SettingsScreen.name) {popUpTo(Screens.MainScreen.name)}
                selected = "settings"
            },
            unselectedContentColor = Color.LightGray,
            selectedContentColor = Color.White,
            icon = {
                Icon(
                    painterResource(id =
                    if (selected == "settings"){
                        R.drawable.baseline_settings_24_white
                    }else{
                        R.drawable.outline_settings_24 }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        )
    }
}


enum class Screens {
    LoadingScreen,
    MainScreen,
    ArkivScreen,
    FavorittScreen,
    SettingsScreen,
    OmOssScreen,
    ReportScreen
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExtremeSportTheme {
        App()
    }
}