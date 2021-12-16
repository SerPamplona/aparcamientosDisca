package com.sergiop.aparcamientos.ui.activitys.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sergiop.aparcamientos.model.Persona
import com.sergiop.aparcamientos.notifications.AppNotificacionManager
import com.sergiop.aparcamientos.settings.data.UserSettings
import com.sergiop.aparcamientos.ui.activitys.editprofile.EditProfileActivity
import com.sergiop.aparcamientos.ui.activitys.home.screens.home.HomeScreen
import com.sergiop.aparcamientos.ui.activitys.home.screens.home.HomeViewModel
import com.sergiop.aparcamientos.ui.activitys.login.LoginActivity
import com.sergiop.aparcamientos.ui.theme.AparcamientosTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var userSetting: UserSettings

    @Inject
    lateinit var usuarioLogged : Persona

    private val homeViewModel : HomeActivityViewModel by viewModels()

    val registerForActivityResult = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            listPermissions ->

        listPermissions.entries.forEach {
            val permissionName = it.key
            val granted = it.value
        }
    }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionsApp()

        homeViewModel.startWatchLocation()

        setContent {
            AparcamientosTheme {
                HomeScreen(this,
                    personaLogged = usuarioLogged,
                    editPhoto = {
                        val intent = Intent (HomeActivity@this, EditProfileActivity::class.java)
                        startActivity(intent)
                    },
                    logout = {
                        userSetting.clearData()
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                )
            }
        }
    }

    private fun requestPermissionsApp() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            registerForActivityResult.launch(arrayOf(   Manifest.permission.ACCESS_FINE_LOCATION))
        } else {
            registerForActivityResult.launch(arrayOf(   Manifest.permission.ACCESS_FINE_LOCATION))
        }
    }

}