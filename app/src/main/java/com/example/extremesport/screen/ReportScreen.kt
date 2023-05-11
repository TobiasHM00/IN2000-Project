package com.example.extremesport.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.extremesport.R
import com.example.extremesport.view.ESViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReportScreen(viewModel: ESViewModel) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState()}
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState)},
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column( // This first column has repeated on different screens, Could be a function for a backdrop. Would also make it easier to change style later
                modifier = Modifier
                    .background("#1C6EAE".color)
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
                    .offset(y = -50.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.jumptime_tekst_whiteontransparent),
                    contentDescription = "Logonavn",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(130.dp)
                        .offset(x = (-17).dp),
                )
                Text(
                    text = "Rapporter",
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier
                        .offset(y = (-30).dp)
                )
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight-150.dp)
                    .padding(20.dp)
                    .offset(y = 100.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Hvilket problem opplever du?")
                    DropDownMenu()
                    Spacer(modifier = Modifier.padding(40.dp))
                    Text(text = "Beskriv problemet")
                    ReportBox(scope, snackbarHostState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var valgt by remember { mutableStateOf("") }
    val list = stringArrayResource(R.array.ListOfProblems)
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = valgt,
            onValueChange = {valgt = it},
            label = { Text(text = "Velg problem")},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            list.forEach { problemer ->
                DropdownMenuItem(
                    text = { Text(text = problemer) },
                    onClick = {
                        valgt = problemer
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBox(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    var buttonClicked by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = text,
        label = { Text(text = "Beskriv problem") },
        modifier = Modifier.height(200.dp),
        onValueChange = { text = it },
        keyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus() }
        )
    )
    //Fiks farge og mulighet til at alt i tekstboks forsvinner
    TextButton(
        onClick = {
            focusManager.clearFocus()
            text = TextFieldValue("")
            buttonClicked = false
            scope.launch { snackbarHostState.showSnackbar(
                message = "Takk for feedback",
                duration = SnackbarDuration.Short
            ) }
        },
        enabled = text.text.isNotEmpty() && !buttonClicked,
    ) {
        Text(text = "Send", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}


