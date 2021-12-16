package com.sergiop.aparcamientos.ui.activitys.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sergiop.aparcamientos.ui.theme.Shapes

class Register {
    @Composable
    fun Register(mContext : Context) {

        Column(modifier = Modifier.height(16.dp)) {
            HeaderText()
            Spacer(modifier = Modifier.height(64.dp))
            latitudParking()
            Spacer(modifier = Modifier.height(4.dp))
            longitudParking()
            Spacer(modifier = Modifier.height(16.dp))
            ButtonGeoLocalizacion()
            Spacer(modifier = Modifier.height(64.dp))
            LargoTextField()
            Spacer(modifier = Modifier.height(4.dp))
            AnchoTextField()
            Spacer(modifier = Modifier.height(16.dp))
            MotoCb()
            Spacer(modifier = Modifier.height(64.dp))
            //ButtonAdd(mContext)
            Spacer(modifier = Modifier.height(68.dp))
            //com.sergiop.aparcamientos.ui.activitys.register.ButtonToLogin(onClick = {
            //mContext.startActivity(Intent(mContext, LoginActivity::class.java))
            //} )
        }
    }

    @Composable
    private fun HeaderText(){
        Text(text = "Create Plus Your Parking", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text(text = "Sing un to get started", fontWeight = FontWeight.Bold, color = Color.LightGray)
    }

    @Composable
    private fun latitudParking(){
        var latitud by remember { mutableStateOf("") }

        OutlinedTextField(
            value = latitud,
            onValueChange = {latitud = it},
            label = { Text(text = "latitud") },
            modifier = Modifier.fillMaxWidth(),
        )
    }

    @Composable
    private fun longitudParking(){
        var longitud by remember { mutableStateOf("") }

        OutlinedTextField(
            value = longitud,
            onValueChange = {longitud = it},
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth(),
        )
    }

    @Composable
    private fun ButtonGeoLocalizacion(){
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal =16.dp),
            shape = Shapes.large
        ) {
            Text(text = "Register")
        }
    }


    @Composable
    private fun LargoTextField(){
        var largo by remember { mutableStateOf("") }

        OutlinedTextField(
            value = largo,
            onValueChange = {largo = it},
            label = { Text(text = "largo de la plaza") },
            modifier = Modifier.fillMaxWidth(),
        )
    }

    @Composable
    private fun AnchoTextField(){
        var ancho by remember { mutableStateOf("") }

        OutlinedTextField(
            value = ancho,
            onValueChange = {ancho = it},
            label = { Text(text = "ancho de la plaza") },
            modifier = Modifier.fillMaxWidth(),
        )
    }

    @Composable
    private fun MotoCb(/*parking : ParkingViewModel*/){
        Checkbox(
            checked = false,
            onCheckedChange = null
            //onCheckedChange = { checked -> formState.optionChecked = checked
        )
        Text(text = "this is parkinf of moto?")
    }
/*
@Composable
private fun ButtonAdd(taskParking : TaskParkingViewModel){
    Button(
        onClick = { login.login() },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal =16.dp),
        shape = Shapes.large
    ) {
        Text(text = "incluir plaza")
    }
}
@Composable
private fun ButtonDelete(taskParking : TaskParkingViewModel){
    Button(
        onClick = { login.login() },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal =16.dp),
        shape = Shapes.large
    ) {
        Text(text = "eliminar plaza")
    }
}
@Composable
private fun ButtonUpdate(taskParking : TaskParkingViewModel){
    Button(
        onClick = { login.login() },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal =16.dp),
        shape = Shapes.large
    ) {
        Text(text = "modificar plaza")
    }
}
@Composable
private fun ButtonGestor(){
    Column() {
        Spacer(modifier = Modifier.height(34.dp))
        HeaderText()
        Spacer(modifier = Modifier.height(50.dp))
        ButtonAdd(taskParking = null)
        Spacer(modifier = Modifier.height(50.dp))
        ButtonDelete(taskParking = null)
        Spacer(modifier = Modifier.height(50.dp))
        ButtonUpdate(taskParking = null)
    }
}
*/
}