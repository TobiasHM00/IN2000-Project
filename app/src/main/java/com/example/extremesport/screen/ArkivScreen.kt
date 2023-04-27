package com.example.extremesport.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R

@Composable
fun ArkivScreen(navController: NavController){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .alpha(1f)
                .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .padding(5.dp)
                .align(Alignment.BottomCenter)
                .height(650.dp)
                .fillMaxWidth()
        ) {}
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
            Spacer(modifier = Modifier.padding(15.dp))
            Text(
                text = "Arkiv - Alle dine hopp!",
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
                .height(650.dp)
                .fillMaxWidth()
        ) {
            Column() {
                LazyColumn {
                    //Dette må være antall stasjoner brukeren har hoppet
                    items(1) {
                        Cards()
                    }
                }
            }
        }
    }
}

@Composable
fun Cards() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "StasjonNavn",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp)
                //Rating må saves på en eller annen måte på hver stasjon
                Rating(rating = 3)
            }
            Text(text = "Mer informasjon")
        }
    }
}

@Composable
fun Rating(rating: Int) {
    var ratingState by remember { mutableStateOf(rating) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painterResource(id = R.drawable.baseline_star_24_grey),
                contentDescription = "Tom stjerne",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        ratingState = i
                    },
                tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
            )
        }
    }
}