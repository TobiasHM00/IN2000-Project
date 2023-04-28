package com.example.extremesport

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.extremesport.screen.*
import com.example.extremesport.ui.theme.ExtremeSportTheme

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
                    App(this)
                }
            }
        }
    }
}

@Composable
fun App(context: Context) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.name
    ) {
        composable(Screens.MainScreen.name) {
            MainScreen(navController = navController, context)
        }
        composable(Screens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
        composable(Screens.ArkivScreen.name) {
            ArkivScreen(navController = navController)
        }
        composable(Screens.FavorittScreen.name) {
            FavorittScreen(navController = navController)
        }
        composable(Screens.ReportScreen.name) {
            ReportScreen(navController = navController)
        }
        composable(Screens.OmOssScreen.name) {
            OmOssScreen(navController = navController)
        }
    }
}

enum class Screens() {
    MainScreen,
    ArkivScreen,
    FavorittScreen,
    SettingsScreen,
    OmOssScreen,
    ReportScreen
}
/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExtremeSportTheme {
       App(context = )
    }
}
 */