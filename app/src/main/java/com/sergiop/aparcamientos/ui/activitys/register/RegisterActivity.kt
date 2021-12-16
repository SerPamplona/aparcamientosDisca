package com.sergiop.aparcamientos.ui.activitys.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.sergiop.aparcamientos.ui.activitys.login.LoginActivity
import com.sergiop.aparcamientos.ui.theme.AparcamientosTheme
import com.sergiop.aparcamientos.ui.theme.Shapes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

    private val registerModel : RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AparcamientosTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Register(mContext = this@RegisterActivity);
                }
            }
        }
    }

    @Composable
    fun Register(mContext : Context) {
        val stateLogin = registerModel.registerUser.collectAsState()
        if (stateLogin.value.isSuccess) {
            val openDialog = remember {
                mutableStateOf(true)
            }
            successLayout()
            successRegister("Registro completado", openDialog)
        } else {
            formRegister()
        }
    }

    @Composable
    fun successLayout () {
        Column {
            Text("¡¡Registro completado con ÉXITO!!")
            Spacer(modifier = Modifier.height(64.dp))
            Text("Vuelve al login para logearte")
            Spacer(modifier = Modifier.height(4.dp))
            ButtonAction("Login", onClick = {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            })
        }
    }

    @Composable
    fun successRegister (text : String, openDialog : MutableState<Boolean>) {

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Registo completo")
                },
                text = {
                    Text(text)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("OK")

                        //que ha estos botones, se les puede dar la funcionalidad que se quiera??
                        //¿hay otros tipos de dialogos?.
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

    @Composable
    fun formRegister () {
        Column {
            HeaderText()
            Spacer(modifier = Modifier.height(64.dp))
            UsernameTextField()
            Spacer(modifier = Modifier.height(4.dp))
            TelfTextField()
            Spacer(modifier = Modifier.height(4.dp))
            PasswordTextField()
            Spacer(modifier = Modifier.height(64.dp))
            ButtonAction("Register", onClick = { registerModel.register() })
        }
    }

    @Composable
    private fun HeaderText(){
        Text(text = "Create Account", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text(text = "Sing un to get started", fontWeight = FontWeight.Bold, color = Color.LightGray)
    }

    @Composable
    private fun UsernameTextField(){
        var username by remember { mutableStateOf("") }

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                registerModel.username = username
            },
            label = { Text(text = "Username")},
            modifier = Modifier.fillMaxWidth(),
        )
    }

    @Composable
    private fun TelfTextField(){
        var telefono by remember { mutableStateOf("") }

        OutlinedTextField(
            value = telefono,
            onValueChange = {
                telefono = it
                registerModel.telefono = telefono},
            label = { Text(text = "Telefono")},
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }

    @Composable
    private fun PasswordTextField(){
        var password by remember { mutableStateOf("") }

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                registerModel.password = password
            },
            label = { Text(text = "Password")},
            modifier = Modifier.fillMaxWidth(),
        )
    }

    @Composable
    private fun ButtonAction(texto: String, onClick: () -> Unit){
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal =16.dp),
            shape = Shapes.large
        ) {
            Text(text = texto)
        }
    }

}
