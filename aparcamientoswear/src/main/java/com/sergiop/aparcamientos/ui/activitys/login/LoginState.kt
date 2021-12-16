package com.sergiop.aparcamientos.ui.activitys.login

import com.sergiop.aparcamientos.model.Persona

data class LoginState (
    val isLoading: Boolean = true,
    val requestSend: Boolean = false,
    val userLogged: Persona? = null,
    val error: Boolean = false,
    val messageError : String = "",
)