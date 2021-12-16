package com.sergiop.aparcamientos.ui.activitys.home.screens.listParking

import com.sergiop.aparcamientos.model.Garaje

data class ListParkingState (
    val loadingList: Boolean = false,
    val list: List<Garaje>? = null,
    val error: Boolean = false,
    val messageError : String = ""
)