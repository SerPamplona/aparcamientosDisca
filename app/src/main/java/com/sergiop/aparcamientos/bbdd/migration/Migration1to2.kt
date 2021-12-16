package com.sergiop.aparcamientos.bbdd.migration

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migration1to2 : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema

        if ((oldVersion == 0L) &&  (newVersion == 2L)) {
            val userSchema = schema["PersonaBBDD"]
            userSchema!!.addField("edad", Int::class.java)
        }
    }
}
