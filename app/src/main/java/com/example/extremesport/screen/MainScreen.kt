package com.example.extremesport.screen

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color.parseColor
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R
import com.example.extremesport.Screens
import com.example.extremesport.view.ESViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen (navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val viewModel = ESViewModel()
    //Thread.sleep(5000)
    //println("vente 5sek\n\n")
    //val testBoolean  = viewModel.checkRequirements("Fallskjermhopping")
    //val testBoolean2  = viewModel.checkRequirements("Testing")

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        BorderStroke(1.dp, Color.Black),
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentColor = Color.Black,
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState
                            .showSnackbar("hei")
                        }
                     },
                backgroundColor = Color.White
            )
            {
                Icon(imageVector = Icons.Default.Add, null)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,

        drawerContent = { DrawerMenu(navController, scaffoldState, coroutineScope) },
        drawerGesturesEnabled = false,

        bottomBar = {
            BottomAppBar(
                //cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
                modifier = Modifier
                    .height(65.dp),
                backgroundColor = "#1C6EAE".color
            )
            {
                BottomNavigationItem(
                    selected = false,
                    onClick = { coroutineScope.launch { scaffoldState.drawerState.open()} },
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.White,
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.baseline_menu_24_white),
                            contentDescription = "Menyknapp",
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.padding(130.dp))

                BottomNavigationItem(
                    selected = false,
                    onClick =  { navController.navigate(Screens.SettingsScreen.name) {popUpTo(Screens.MainScreen.name)} },
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.White,
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.baseline_settings_24_white),
                            contentDescription = "Settingsknapp",
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                )
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Map()
            }
            Box(Modifier.border(width = 1.dp, Color.Black, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))) {
                ShowWeatherBox(viewModel)
            }
        }
    )
}
@Composable
fun Map(){
    val tromsoo = LatLng(69.67575, 18.91752)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tromsoo, 6f)
    }
    val context = LocalContext.current

    var uiSettings by remember { mutableStateOf(MapUiSettings()) }

    val mapProperties by remember { mutableStateOf(MapProperties(mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)))}

    val googleMap = GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings =  uiSettings,
        properties = mapProperties
    ) {
        Markers()
    }
}

@Composable
fun Markers(){
    val tromsoo = LatLng(69.67575, 18.91752)
    val troms = LatLng(69.05894, 18.54549)
    val bodoo = LatLng(67.27268, 14.41794)
    val ntnu = LatLng(63.89993, 10.36208)
    val oppdal = LatLng(62.65002, 9.85408)
    val fooniks = LatLng(62.74936, 7.26345)
    val lesja = LatLng(62.23288, 8.25007)

    Marker(
        state = MarkerState(position = tromsoo)
        //, onClick = {  }
    )
    Marker(
        state = MarkerState(position = troms)
    )
    Marker(
        state = MarkerState(position = bodoo)
    )
    Marker(
        state = MarkerState(position = ntnu)
    )
    Marker(
        state = MarkerState(position = oppdal)
    )
    Marker(
        state = MarkerState(position = fooniks)
    )
    Marker(
        state = MarkerState(position = lesja)
    )
}
@Composable
fun ShowWeatherBox(viewModel: ESViewModel) {
    val sizeOfDevice = LocalConfiguration.current
    val screenHeight = sizeOfDevice.screenHeightDp

    var height by remember { mutableStateOf((screenHeight-(screenHeight/4)).dp) }
    var picture by remember { mutableStateOf(R.drawable.arrowdown) }
    var keyword by remember { mutableStateOf("long") }
    //TODO fjerne?
    var buttonText by remember { mutableStateOf("Show more") }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.LightGray, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .alpha(1f)
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .padding(5.dp)
    ) {
        InformationBox(keyword, viewModel)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (height == (screenHeight/4.5).dp) {
                        height = (screenHeight-(screenHeight/4)).dp
                        picture = R.drawable.arrowup
                        keyword = "long"
                        buttonText = "Show less"
                    } else {
                        height = (screenHeight/4.5).dp
                        picture = R.drawable.arrowdown
                        keyword = "short"
                        buttonText = "Show more"
                    }
                },
                Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .background(Color.LightGray)
            ) {
                //Text(buttonText)
                Image(painter = painterResource(id = picture), contentDescription = null,
                    Modifier
                        .fillMaxSize()
                        .background(Color.LightGray))
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun InformationBox(keyword: String, viewModel: ESViewModel) {
    when (keyword) {
        "short" -> {
            ShortInformationBox(viewModel = viewModel)
        }
        "long" -> {
            LongInformationBox(viewModel = viewModel)
        }
        else -> {

        }
    }
}

@Composable
fun ShortInformationBox(viewModel: ESViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 20.dp, top = 10.dp)
            ) {
                Text("Sted")
                Text("Værforhold")
                Text("Temp: ")
                Text("H: & L: 3")
                Text("Vindinfo: 5 m/s")
            }
            Spacer(Modifier.padding(50.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sikkerhetsnivå")
                Image(
                    //TODO: dette må være en variabel og ikke et fast icon
                    //TODO: kommer ann på hva checkrequerments sier
                    painter = painterResource(id = R.drawable.red_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(50.dp)
                )
            }
        }
    }
}

@Composable
fun LongInformationBox(viewModel: ESViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 20.dp, top = 10.dp)
            ) {
                Text("Sted")
                Text("Værforhold")
                Text("Temp: ")
                Text("H: & L: 3")
                Text("Vindinfo: 5 m/s")
            }
            Spacer(Modifier.padding(50.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sikkerhetsnivå")
                Image(
                    //TODO: dette må være en variabel og ikke et fast icon
                    //TODO: kommer ann på hva checkrequerments sier
                    painter = painterResource(id = R.drawable.green_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(50.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        //Info om stedet
        Column(
            Modifier.padding(start = 20.dp)
        ) {
            Text("Oslo fallskjermklubb", fontSize = 20.sp)
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.marker),
                        contentDescription = null,
                        Modifier.size(40.dp)
                    )
                    Text("Oslo gate 5, 0882, Oslo")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.clock_icon),
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                    Text("9-17")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.internett_icon),
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                    Text("fallskjerm.no")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.phone_icon),
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                    Text("876 54 321")
                }
            }
        }
    }
}

@Composable
fun DrawerMenu(navController: NavController, scaffoldState: ScaffoldState, coroutineScope: CoroutineScope) {
    Column {
        Column(
            modifier = Modifier
                .background("#1C6EAE".color)
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .size(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_account_circle_24_white),
                        contentDescription = "Profilbilde",
                        modifier = Modifier
                            .size(150.dp),
                        tint = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "JumpTime",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White,
                    modifier = Modifier.width(IntrinsicSize.Min).padding(end = 6.dp, start = 6.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "Poeng:  ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.width(IntrinsicSize.Min)
                    )
                    Text(
                        text = "<x>",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.width(IntrinsicSize.Min)
                    )
                }
            }
        }
        //Profil, scoreboards osv
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(
                onClick = { navController.navigate(Screens.FavorittScreen.name) {popUpTo(Screens.MainScreen.name)} },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_save_24),
                    contentDescription = "Favoritter ikon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Favoritter", fontWeight = FontWeight.Bold)
            }
            TextButton(
                onClick = { navController.navigate(Screens.ArkivScreen.name) {popUpTo(Screens.MainScreen.name)} },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_archive_24),
                    contentDescription = "Arkiv",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Arkiv", fontWeight = FontWeight.Bold)
            }
            TextButton(
                onClick = { navController.navigate(Screens.SettingsScreen.name) {popUpTo(Screens.MainScreen.name)} },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = "Innstillinger ikon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Innstillinger", fontWeight = FontWeight.Bold)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                TextButton(
                    onClick = { navController.navigate(Screens.OmOssScreen.name) {popUpTo(Screens.MainScreen.name)} },
                ) {
                    Image(
                        painterResource(id = R.drawable.baseline_groups_24),
                        contentDescription = "Om oss ikon",
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = "Om oss", fontWeight = FontWeight.Bold)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = { navController.navigate(Screens.ReportScreen.name) {popUpTo(Screens.MainScreen.name)} },
                    ) {
                        Image(
                            painterResource(id = R.drawable.baseline_report_problem_24),
                            contentDescription = "Rapporter ikon",
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = "Rapporter", fontWeight = FontWeight.Bold)
                    }
                    TextButton(
                        onClick = { coroutineScope.launch { scaffoldState.drawerState.close()} }
                        ) {
                        Text(text = "Tilbake -->", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

val String.color get() = Color(parseColor(this))