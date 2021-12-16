package com.sergiop.aparcamientos.ui.activitys.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.model.Persona
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository : DataRepository): ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _state

    var username : String = ""
    var password : String = ""


    fun login () {

        viewModelScope.launch {
            _state.value = LoginState(isLoading = true)
            val valueResult = viewModelScope.async {repository.loginUser(username, password = password)}
            val result = valueResult.await()

            if (result.isSuccess) {
                _state.value = LoginState(isLoading = false, userLogged = result.getOrNull())
            } else {
                _state.value = LoginState(isLoading = false, error = true, messageError = result.exceptionOrNull()?.message?: "Error login")
            }
        }

    }

}