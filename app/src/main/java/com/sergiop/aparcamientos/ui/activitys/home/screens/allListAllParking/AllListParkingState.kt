package com.sergiop.aparcamientos.ui.activitys.home.screens.allListAllParking

import com.sergiop.aparcamientos.model.Garaje

data class AllListParkingState (
    val loadingList: Boolean = false,
    val list: List<Garaje>? = null,
    val error: Boolean = false,
    val messageError : String = ""
)