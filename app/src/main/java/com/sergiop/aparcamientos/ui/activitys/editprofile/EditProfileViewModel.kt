package com.sergiop.aparcamientos.ui.activitys.editprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiop.aparcamientos.model.Persona
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.storage.DataStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor( private val storage : DataStorage,
                                                private val database : DataRepository): ViewModel() {

    @Inject
    lateinit var usuarioLogged : Persona

    var observerUploadImage = MutableStateFlow<Result<String?>>(Result.success(null))

    fun uploadFile (imageUri : Uri) {

        viewModelScope.launch {
            val valueResult = async { storage.uploadImage(imageUri) }
            val result = valueResult.await()

            if (result.isSuccess) {
                val valueResultEditImage = async {database.editImageUser(usuarioLogged, result.getOrNull()?:"")}.await()
                if (valueResultEditImage.isSuccess) {
                    observerUploadImage.value = result
                } else {
                    observerUploadImage.value = result
                }
            } else {
                observerUploadImage.value = result
            }
        }
    }
}