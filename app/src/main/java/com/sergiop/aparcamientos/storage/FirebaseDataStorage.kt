package com.sergiop.aparcamientos.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataStorage @Inject constructor(private val storage : FirebaseStorage) : DataStorage {

    override suspend fun uploadImage(imageUri : Uri): Result<String> {
        val imagesRef = storage.reference.child("images/${imageUri.lastPathSegment}")
        val task = imagesRef.putFile(imageUri)

        val document = task.await()
        if (task.isSuccessful) {
            val urlDownload = imagesRef.downloadUrl.await()
            return Result.success(urlDownload.toString())
        } else {
            return Result.failure(exception = Throwable("Not access to storage Firebase"))
        }
    }

    /*
    Estuve mirando esto, entender lo entiendo, pero si me toca hacerlo, tendria que estar mirandolo como ejemplo
    Estuve pensando y planeando, como hacer unas cosillas, respecto a lo de las listas y demas
    Pero prediero terminar esta parte, por terminarla, y si la hemos terminado, dejar a que intente hacer lo que tengo pensado.
    de la forma que pense
    */

}
