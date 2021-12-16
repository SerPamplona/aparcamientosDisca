package com.sergiop.aparcamientos.ui.activitys.editprofile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import com.sergiop.aparcamientos.ui.activitys.components.Register
import com.sergiop.aparcamientos.ui.theme.AparcamientosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private val editProfileViewModel : EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            AparcamientosTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    screenMain()
                }
            }
        }

   }

    @Composable
    fun screenMain() {

        val pickPictureLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { imageUri ->
            imageUri?.let {
                editProfileViewModel.uploadFile(imageUri = it)
            }
        }

        val stateChange = editProfileViewModel.observerUploadImage.collectAsState()
        if ((stateChange.value.isSuccess) && (!stateChange.value.getOrNull().isNullOrEmpty())) {
            val openDialog = remember {
                mutableStateOf(true)
            }
            successLayout()
        }

        editProfile(
            {
                pickPictureLauncher.launch("image/*")
            }
        )
    }

    @Composable
    fun successLayout () {
        Column {
            Text("¡¡Imagen cambiada con éxito con ÉXITO!!")
            Spacer(modifier = Modifier.height(64.dp))
            Text("Vuelve al login para logearte")
        }
    }

    @Composable
    fun editProfile (clickEditProfile : () -> Unit) {
        Button(onClick = clickEditProfile) {
            Text(text = "Editar foto de perfil")
        }
    }
}