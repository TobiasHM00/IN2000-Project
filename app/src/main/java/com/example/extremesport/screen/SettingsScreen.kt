package com.example.extremesport.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R
import com.example.extremesport.view.ESViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController, viewModel: ESViewModel) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .background("#1C6EAE".color)
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
        ) {
            Text(
                text = "LOGONAVN",
                color = Color.White
            )
            Text(
                text = "Settings",
                fontSize = 30.sp,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                )
                .alpha(1f)
                .clip(shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .padding(5.dp)
                .align(Alignment.BottomCenter)
                //Kan være vi bør gjøre den mindre m tanke på at vi har få settings
                .height(screenHeight - 120.dp)
                .fillMaxWidth()
        ) {
            Setting(
                description = "Night Mode",
                contentDescription = "Logo for night mode",
                buttonText = "Test"
            ) {
                // What the setting does here
            }
            Setting(
                description = "Ny setting",
                //icon = "new image here",
                contentDescription = "Ny setting",
                buttonText = "Test"
            ) {

            }
        }
    }
}

@Composable
fun Setting(
    description: String,
    icon: Painter = painterResource(id = R.drawable.baseline_mode_night_24_black), // Maybe change to another default image
    contentDescription: String = "",
    buttonText: String = "",
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier.padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Text(
            text = description,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onClick) {
            Text(text = buttonText)
        }
    }
}


