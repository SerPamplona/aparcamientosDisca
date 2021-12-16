package com.sergiop.aparcamientos.ui.activitys.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sergiop.aparcamientos.ui.activitys.login.LoginViewModel

@Composable
fun loginIndex (loginViewModel: LoginViewModel,
                startLocation : () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        loginBody(loginViewModel = loginViewModel)
        locationBody(loginViewModel = loginViewModel,
                startLocation = startLocation)
    }
}

//////////////////////////
/// COMPONENTES LOGIN
//////////////////////////

@Composable
fun loginBody(loginViewModel: LoginViewModel) {

    val stateLogin = loginViewModel.loginState.value

    
    if (stateLogin.error) {
        KOBody(    stateLogin.messageError,
            retryAgain = {loginViewModel.login()})
    } else if ((!stateLogin.error) && (stateLogin.userLogged != null)) {
        OKBody("Usuario Logueado\n" + stateLogin.userLogged?.username,
            retryAgain = {loginViewModel.login()})
    } else if (stateLogin.isLoading){
        loadingBody("Logueando usuario...")
    }
}
//////////////////////////
//////////////////////////
//////////////////////////
//////////////////////////





//////////////////////////
/// COMPONENTES LOCATION
//////////////////////////
@Composable
fun locationBody(loginViewModel: LoginViewModel,
                startLocation: () -> Unit) {

    val stateLocation = loginViewModel.locationState.value

    if (stateLocation.error) {
        KOBody(    stateLocation.messageError,
                retryAgain = {startLocation})
    } else if ((!stateLocation.error) && (stateLocation.location != null)) {
        OKBody("Localización\n " +
                "${stateLocation.location.longitude}, ${stateLocation.location.latitude}",
            retryAgain = startLocation)
    } else if (stateLocation.isLoading){
        loadingBody("Buscando localización...")
    }
}


//////////////////////////
//////////////////////////
//////////////////////////
//////////////////////////



@Composable
fun loadingBody(messageInfo : String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = messageInfo)
    }
}

@Composable
fun OKBody(messageOK : String,
           retryAgain : () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(messageOK)
        Button(onClick = retryAgain) {
            Text(text = "Intentar de nuevo")
        }
    }
}

@Composable
fun KOBody(messageError : String,
           retryAgain : () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(messageError)

        Button(onClick = retryAgain) {
            Text(text = "Intentar de nuevo")
        }
    }
}