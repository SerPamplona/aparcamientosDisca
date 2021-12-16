package com.sergiop.aparcamientos.ui.activitys.home.screens.home

import android.content.Context
import android.content.Intent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.sergiop.aparcamientos.model.Persona
import com.sergiop.aparcamientos.ui.activitys.components.Drawer
import com.sergiop.aparcamientos.ui.activitys.components.DrawerScreens
import com.sergiop.aparcamientos.ui.activitys.home.HomeActivity
import com.sergiop.aparcamientos.ui.activitys.home.HomeRouter
import com.sergiop.aparcamientos.ui.activitys.login.LoginActivity
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun HomeScreen(context : Context,
               personaLogged : Persona,
               editPhoto : () -> Unit,
                logout: () -> Unit) {

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }

    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Drawer(
                personaLogged = personaLogged,
                editPhoto = editPhoto,
                screens = listOf(DrawerScreens.Home,
                    DrawerScreens.AllParkings,
                    DrawerScreens.RegisterParking,
                    DrawerScreens.YourParkings,
                    DrawerScreens.Help),
                onDestinationClicked = { route ->
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route)
                },
                logoutClick = logout)
        }
    ) {
        HomeRouter(
            context = context,
            navController = navController,
            openDrawer = openDrawer
        )
    }

}