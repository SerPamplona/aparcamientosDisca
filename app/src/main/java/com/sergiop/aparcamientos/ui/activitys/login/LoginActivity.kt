package com.sergiop.aparcamientos.ui.activitys.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataItem
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import com.sergiop.aparcamientos.R
import com.sergiop.aparcamientos.activity.COUNT_KEY
import com.sergiop.aparcamientos.activity.USER_KEY
import com.sergiop.aparcamientos.ui.activitys.home.HomeActivity
import com.sergiop.aparcamientos.ui.activitys.register.RegisterActivity
import com.sergiop.aparcamientos.settings.data.UserSettings
import com.sergiop.aparcamientos.model.Persona
import com.sergiop.aparcamientos.notifications.AppNotificacionManager
import com.sergiop.aparcamientos.notifications.NotificationData
import com.sergiop.aparcamientos.ui.activitys.components.PasswordTextfield
import com.sergiop.aparcamientos.ui.theme.AparcamientosTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.sergiop.aparcamientos.ui.theme.Shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private var count = 0

    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var userSetting: UserSettings

    @Inject
    lateinit var dataClientWear : DataClient

    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            checkAutoLogin()
        }

        setContent {
            val navController = rememberNavController()
            Surface {
                NavHost(navController = navController, startDestination = "login") {

                    // login
                    composable("login") {
                    AparcamientosTheme {
                    login(
                        actionRegister = {
                            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

                        },
                        Ok_Login = { persona ->
                            userSetting.id = persona.id
                            userSetting.username = persona.username
                            userSetting.password = persona.password
                            userSetting.image = persona.imagen

                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        },
                        KO_Login = { message -> Toast.makeText(this@LoginActivity, "Login erroneo. Info: ${message}", Toast.LENGTH_LONG).show() },
                        loginViewModel = loginViewModel,
                        checkAutoLogin = { check ->
                            userSetting.autoLogin = check
                        }
                    )
            }
        }

                // register
                composable("loginOk") {
                AparcamientosTheme {
                Text ("LOGIN CORRECTO")
                }
            }
        }
            }
        }

    }

    // Create a data map and put data in it
    suspend private fun increaseCounter(context : Context, user : String) {
        val putDataReq: PutDataRequest = PutDataMapRequest.create("/count").run {
            dataMap.putInt(COUNT_KEY, count ++)
            dataMap.putString(USER_KEY, user)
            dataMap.putLong("Time",System.currentTimeMillis());
            asPutDataRequest()
        }
        val putDataTask: Task<DataItem> = dataClientWear.putDataItem(putDataReq)
        val dataItem = putDataTask.await()

        Toast.makeText(context, "Enviado a reloj ${dataItem}", Toast.LENGTH_LONG).show()
    }

    suspend private fun checkAutoLogin () {
        if (userSetting.autoLogin) {
            loginViewModel.username = userSetting.username
            loginViewModel.password = userSetting.password

            loginViewModel.login()
        }
    }

}

@Composable
fun stateSampleComponent(   text : String,
                            onValueStateComponentChange : (String) -> Unit) {

    Column {
        Text(text)
        Spacer(Modifier.height(10.dp))
        Text("Pulsa el boton para limpiar")

        TextField(value = text,
            onValueChange = {
                onValueStateComponentChange(it)
            }
        )

        Button(onClick = {
            onValueStateComponentChange("")
        }) {
            Text(text = "Limpiar")
        }
    }
}

/// COMPONENTES LOGIN
@Composable
fun login(      actionRegister : () -> Unit,
                Ok_Login : (Persona) -> Unit,
                KO_Login: (String) -> Unit,
                loginViewModel: LoginViewModel,
                checkAutoLogin: (Boolean) -> Unit) {

    val stateLogin = loginViewModel.loginState.value

    loginBody(loginViewModel = loginViewModel, actionRegister = actionRegister, checkAutoLogin = checkAutoLogin)

    if (stateLogin.error) {
        KO_Login(stateLogin.messageError)
    } else if ((!stateLogin.error) && (stateLogin.userLogged != null)) {
        Ok_Login(stateLogin.userLogged)
    } else if (stateLogin.isLoading){
        loginLoading()
    }
}

@Composable
private fun HeaderText(){
    Text(text = "Welcome", fontWeight = FontWeight.Bold, fontSize = 32.sp)
}

@Composable
private fun loginLoading () {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun loginBody(loginViewModel : LoginViewModel,
                      actionRegister: () -> Unit,
                      checkAutoLogin: (Boolean) -> Unit){

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        HeaderText()
        Spacer(modifier = Modifier.height(38.dp))
        UserNameTextField(loginViewModel)
        Spacer(modifier = Modifier.height(4.dp))

        PasswordTextfield(
            iconVisible = painterResource(id = R.drawable.icon_visible),
            iconInvisible = painterResource(id = R.drawable.icon_invisible),
            onValueChange = {loginViewModel.password = it})

        Spacer(modifier = Modifier.height(64.dp))
        checkAutoLogin(checkAutoLogin)
        Spacer(modifier = Modifier.height(64.dp))

        ButtonLogin(loginViewModel)
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(128.dp))
        ButtonToRegister (onClick = {
            actionRegister()
        })
    }
}

@Composable
private fun checkAutoLogin (onChecked : (Boolean) -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        var isChecked by remember {
            mutableStateOf(false)
        }

        Text(text = "¿Autologin GITHUB?")
        Spacer(modifier = Modifier.height(20.dp))
        Switch(checked = isChecked, onCheckedChange = {
                isChecked = it
                onChecked(it)
        })
    }
}

@Composable
private fun UserNameTextField(viewModel: LoginViewModel) {
    var username by remember { mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                viewModel.username = it
            },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth(),
        )
    }

}

@Composable
private fun ButtonLogin(login : LoginViewModel){
    Button(
        onClick = { login.login() },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal =16.dp),
        shape = Shapes.large
    ) {
        Text(text = "Login")
    }
}

@Composable
private fun ButtonToRegister(onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal =16.dp),
        shape = Shapes.large
    ) {
        Text(text = "Register")
    }
}











