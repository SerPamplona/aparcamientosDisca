package com.sergiop.aparcamientos.ui.activitys.home

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sergiop.aparcamientos.model.Persona
import com.sergiop.aparcamientos.ui.activitys.components.DrawerScreens
import com.sergiop.testnavcontroller.screens.*
import kotlinx.coroutines.Job

@ExperimentalMaterialApi
@Composable
fun HomeRouter (
    context: Context,
    navController: NavHostController,
    openDrawer: () -> Job) {

    NavHost(
        navController = navController,
        startDestination = DrawerScreens.Home.route
    ) {
        composable(DrawerScreens.Home.route) {
            Home(
                openDrawer = {
                    openDrawer()
                },
                context = context)
        }
        composable(DrawerScreens.AllParkings.route) {
            ListAllParkings(
                openDrawer = {
                    openDrawer()
                },
                context = context
            )
        }
        composable(DrawerScreens.RegisterParking.route) {
            RegisterParking(
                openDrawer = {
                    openDrawer()
                },
                context = context
            )
        }
        composable(DrawerScreens.YourParkings.route) {
            ListParkings(
                openDrawer = {
                    openDrawer()
                },
                context = context)
        }
        composable(DrawerScreens.Help.route) {
            Help(
                openDrawer = {
                    openDrawer()
                }
            )
        }
    }
}