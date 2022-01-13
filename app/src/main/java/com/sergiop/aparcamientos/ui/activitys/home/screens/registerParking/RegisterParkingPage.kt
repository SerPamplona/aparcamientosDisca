package com.sergiop.aparcamientos.ui.activitys.home.screens.registerParking

import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sergiop.aparcamientos.ui.activitys.home.HomeActivity

@Composable
fun RegisterParkingPage(mContext : Context,
                        registerViewModel: RegisterViewModel = hiltViewModel()) {

    val stateLogin = registerViewModel.registerParkingState.value

    val scrollState = rememberScrollState()
    val registerState = registerViewModel.observerRegisterGarage.collectAsState(Result.failure(Throwable()))

    Column (modifier = Modifier.verticalScroll( enabled = true,//le asigna un scroll
        state = scrollState)) {
        HeaderText()
        Spacer(modifier = Modifier.height(24.dp))
        direccionParking(registerViewModel)
        registerLocationParkingUI(viewModel = registerViewModel)
        Spacer(modifier = Modifier.height(10.dp))
        ButtonRefresh (onRefresh = {
            val intent = Intent(mContext, HomeActivity::class.java)
            mContext.startActivity(intent)
        }
        )
    }

    //comprueba si el registro de la plaza se a hecho correctamente
    if (registerState.value.isSuccess) {
        Snackbar(
            action = {
                Button(onClick = {
                    registerViewModel.observerRegisterGarage.value = Result.failure(Throwable())
                }) {
                    Text("Ok")
                }
            },
        ) {
            Text ("Plaza registrada correctamente")
        }
    }

}

@Composable
private fun HeaderText(){
    Text(text = "Create Plus Your Parking", fontWeight = FontWeight.Bold, fontSize = 32.sp)
    Text(text = "Sing un to get started", fontWeight = FontWeight.Bold, color = Color.LightGray)
}

//este metodo recive Location, y si esta seleccionado, y retorna String de latitud
@Composable
private fun latitudParking(location : Location, enable : Boolean) : String {

    var latitudeString by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = if (enable) {latitudeString} else {location.latitude.toString()},
        onValueChange = {
            latitudeString = it
        },
        label = { Text(text = "Latitud")},
        modifier = Modifier.fillMaxWidth(),
        enabled = enable
    )

    return latitudeString
}

@Composable
private fun longitudParking(location : Location, enable : Boolean) : String {

    var longitudString by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = if (enable) {longitudString} else {location.longitude.toString()},
        onValueChange = {
            longitudString = it
        },
        label = { Text(text = "Longitud")},
        modifier = Modifier.fillMaxWidth(),
        enabled = enable
    )

    return longitudString
}

@Composable
private fun registerLocationParkingUI (viewModel: RegisterViewModel) {

    var currentLocationObject = viewModel.currentLocationObject.collectAsState()


    Spacer(modifier = Modifier.height(16.dp))
    val currentLocationEnable = ButtonLocation(viewModel)
    Spacer(modifier = Modifier.height(16.dp))
    val latitudeState = latitudParking(currentLocationObject.value, !currentLocationEnable)
    Spacer(modifier = Modifier.height(16.dp))
    val longitudState = longitudParking(currentLocationObject.value, !currentLocationEnable)
    LargoTextField(viewModel)
    Spacer(modifier = Modifier.height(4.dp))
    AnchoTextField(viewModel)
    Spacer(modifier = Modifier.height(16.dp))
    MotoCb(viewModel)
    Spacer(modifier = Modifier.height(64.dp))
    ButtonRegister(onRegister = {
        if (!currentLocationEnable) {
            viewModel.setLocationLatitude(latitudeState.toDouble())
            viewModel.setLocationLongitude(longitudState.toDouble())
        }

        viewModel.registerParking()
    })


}

@Composable
private fun direccionParking(viewModel: RegisterViewModel){
    Column() {
        var direccion by remember { mutableStateOf("") }

        OutlinedTextField(
            value = direccion,
            onValueChange = {
                direccion = it
                viewModel.direccion = it.toString()
            },
            label = { Text(text = "Direccion")},
            modifier = Modifier.fillMaxWidth(),
        )
        Text(text = "estas cordenadas que se cargan son las cordenadas que se encuentra actualmente,\n si sabe las cordenadas de su plaza, se pueden remplazar")
    }

}

@Composable
private fun ButtonRegister(onRegister : () -> Unit){
    Button(
        onClick = onRegister,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal =16.dp)
    ) {
        Text(text = "Register")
    }
}

@Composable
private fun ButtonRefresh(onRefresh : () -> Unit){
    Button(
        onClick = onRefresh,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal =16.dp)
    ) {
        Text(text = "Lista Parking")
    }
}

@Composable
private fun ButtonLocation(viewModel: RegisterViewModel) : Boolean {
    var checkedState by remember {
        mutableStateOf(false)
    }
    Row {
        Checkbox(
            checked = checkedState,
            onCheckedChange = {
                checkedState = it
                if (it) viewModel.startLocation()
            }
        )
        Text(text = "Actual location")
    }

    return checkedState
}

@Composable
private fun LargoTextField(viewModel: RegisterViewModel){
    var largo by remember { mutableStateOf("") }

    OutlinedTextField(
        value = largo,
        //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            largo = it
            viewModel.largo = it.toString().toFloat()
        },
        label = { Text(text = "largo de la plaza")},
        modifier = Modifier.fillMaxWidth(),
    )
}
/**
    //Si esta mal lo siento, pienso en java, la metodologia me imagino que estara bien
private Float valorNuumerico (texto : String){
// esta funcion como se encarga de coger el valor numerico, creo que no haria falta que fuera suspend
    cadena : String[] = texto.split()
    numerivo : StringBuilder
    for ( valor : Char in cadena){
        if(valor.toChar().toInt())
            numerico.
    }
}
 **/

@Composable
private fun AnchoTextField(viewModel: RegisterViewModel){
    var ancho by remember { mutableStateOf("") }

    OutlinedTextField(
        value = ancho,
        //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            ancho = it
            viewModel.ancho = it.toString().toFloat()
        },
        label = { Text(text = "ancho de la plaza")},
        modifier = Modifier.fillMaxWidth(),
    )
}
@Composable
private fun MotoCb(viewModel: RegisterViewModel){
    Row {

        var moto by remember {
            mutableStateOf(false)
        }

        Checkbox(
            checked = false,
            onCheckedChange = {
                moto = it
                viewModel.moto = it}
        )
        Text(text = "this is parkinf of moto?")
    }
}