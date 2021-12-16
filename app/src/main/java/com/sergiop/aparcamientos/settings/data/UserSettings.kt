package com.sergiop.aparcamientos.settings.data

import android.content.Context
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.stringPref

class UserSettings (context: Context) : SimpleKrate(context) {
    var id by stringPref("id", "")
    var username by stringPref("username", "")
    var password by stringPref("password", "")
    var image by stringPref("image", "")
    var autoLogin by booleanPref("autologin", false)

    fun clearData() {
        sharedPreferences.edit().clear()
        this.id = null.toString()
        this.username = null.toString()
        this.password = null.toString()
        this.image = null.toString()
        this.autoLogin = false
    }
}
