package com.sergiop.aparcamientos.ui.activitys.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.model.Persona
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository : DataRepository): ViewModel(){

    private var _registerUser = MutableStateFlow<Result<String?>>(Result.failure(Throwable()))
    val registerUser : StateFlow<Result<String?>> = _registerUser

    var username : String = ""
    var telefono : String = ""
    var password : String = ""

    var image : String = ""


    fun register() {

        viewModelScope.launch {
            val valueResult = viewModelScope.async {repository.registerUser(Persona(username = username, telefono = telefono, password = password, imagen = image))}
            _registerUser.value = valueResult.await()
        }

    }

}