package com.sergiop.aparcamientos.ui.activitys.home.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomePage(mContext : Context,
             homeViewModel : HomeViewModel = hiltViewModel()
) {

    val stateHomeState = homeViewModel.parkingState.value

    stateHomeState.nearGarage?.let { parking ->

        Card (elevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Column (modifier = Modifier.padding(10.dp)){
                Row {
                    Text(text = "Direccion: ")
                    Text(text = parking.direccion)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(text = "Ancho: ")
                    Text(text = parking.ancho.toString())
                }
                Row {
                    Text(text = "Largo: ")
                    Text(text = parking.largo.toString())
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(text = "Latitud: ")
                    Text(text = parking.latitud.toString())
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(text = "Longitud: ")
                    Text(text = parking.longitud.toString())
                }
            }
        }
    }

    if ((! stateHomeState.loadingParking) && (stateHomeState.nearGarage == null)) {
        if (stateHomeState.error) {
            showError(stateHomeState.messageError)
        } else {
            emptyCurrentGarage()
        }
    }

    if (stateHomeState.loadingParking) {
        homeLoading()
    }
}

@Composable
fun showError(errorMessage : String) {
    Text(errorMessage)
}

@Composable
fun emptyCurrentGarage() {
    Text("NO EXISTEN PARKING CERCANO")
}

@Composable
private fun homeLoading () {
    /*Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator()
    }*/
    Text("HOME PAGE")
}