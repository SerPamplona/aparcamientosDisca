package com.sergiop.testnavcontroller.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sergiop.aparcamientos.ui.activitys.home.screens.home.HomePage
import com.sergiop.aparcamientos.ui.activitys.home.screens.home.HomeViewModel
import com.sergiop.aparcamientos.ui.activitys.home.screens.listParking.AllParkingPage
import com.sergiop.aparcamientos.ui.activitys.home.screens.listParking.listParkingPage
import com.sergiop.aparcamientos.ui.activitys.home.screens.registerParking.RegisterParkingPage
import com.sergiop.aparcamientos.ui.activitys.info.components.TopBar

@Composable
fun Home(openDrawer: () -> Unit,
         context: Context
) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Home",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        Surface(color = MaterialTheme.colors.background) {
            HomePage(mContext = context)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ListAllParkings(openDrawer: () -> Unit, context: Context) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Lista de parkings",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )

        Surface(color = MaterialTheme.colors.background) {
            AllParkingPage(
                mContext = context
            )
        }
    }
}

@Composable
fun RegisterParking(openDrawer: () -> Unit,
                     context: Context) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Register parking",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        Surface(color = MaterialTheme.colors.background) {
            RegisterParkingPage(
                mContext = context
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ListParkings(openDrawer: () -> Unit, context: Context) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Lista de TUS parkings",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )

        Surface(color = MaterialTheme.colors.background) {
            listParkingPage(
                mContext = context
            )
        }
    }
}

@Composable
fun Help(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Help",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = true, onClick = openDrawer),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Help.", style = MaterialTheme.typography.h4)
        }
    }
}