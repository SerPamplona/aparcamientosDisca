package com.sergiop.aparcamientos.di

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import androidx.room.Room
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.Wearable
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sergiop.aparcamientos.activity.*
import com.sergiop.aparcamientos.bbdd.AppDatabase
import com.sergiop.aparcamientos.bbdd.BBDD
import com.sergiop.aparcamientos.bbdd.RealMBBDD
import com.sergiop.aparcamientos.bbdd.RoomBBDD
import com.sergiop.aparcamientos.bbdd.migration.Migration1to2
import com.sergiop.aparcamientos.settings.data.UserSettings
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.data.FirebaseRepository
import com.sergiop.aparcamientos.data.ServiceRemoteRepository
import com.sergiop.aparcamientos.data.BBDDRepository
import com.sergiop.aparcamientos.model.Persona
import com.sergiop.aparcamientos.notifications.AppNotificacionManager
import com.sergiop.aparcamientos.storage.DataStorage
import com.sergiop.aparcamientos.storage.FirebaseDataStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.ServiceLocationProvider
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataRepository (@ApplicationContext appContext : Context) : DataRepository {
        if (ENVIROMENT == LOCAL) {
            return BBDDRepository(provideBBDD(appContext))
        } else if (ENVIROMENT == REMOTE_HTTP) {
            return ServiceRemoteRepository(provideProviderClientHttp())
        } else {
            return FirebaseRepository(FirebaseFirestore.getInstance())
        }
    }

    @Provides
    @Singleton
    fun provideBBDD(@ApplicationContext appContext : Context) : BBDD {
        if (BBDD_SYSTEM == REALM) {
            Realm.init(appContext)

            val config = RealmConfiguration .Builder()
                .name("Aparcamientos_BBDD")
                .schemaVersion(2L)
                .migration(Migration1to2())
                .build()

            return RealMBBDD(Realm.getInstance(config))
        } else {
            val db = Room.databaseBuilder(
                appContext,
                AppDatabase::class.java, "aparcamientos_bbdd"
            ).build()

            return RoomBBDD(db)
        }
    }

    @Provides
    @Singleton
    fun provideStorage () : DataStorage {
        if (ENVIROMENT == LOCAL) {
            return provideFirebaseStorage()
        } else {
            return provideFirebaseStorage()
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage () : FirebaseDataStorage {
        return FirebaseDataStorage(FirebaseStorage.getInstance())
    }

    @Provides
    @Singleton
    fun provideUserLogged (setting : UserSettings) : Persona {
        return Persona( username = setting.username,
                        password = setting.password,
                        imagen = setting.image,
                        id = setting.id)
    }

    @Provides
    @Singleton
    fun provideSettingsUser (@ApplicationContext appContext : Context) : UserSettings {
        return UserSettings(appContext)
    }

    @Provides
    @Singleton
    fun provideProviderClientHttp () : OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @Singleton
    fun provideProviderLocation () : ServiceLocationProvider {
        val provider = LocationGooglePlayServicesProvider()
        provider.setCheckLocationSettings(true)
        return provider
    }

    @Provides
    @Singleton
    fun provideLocationProvider (@ApplicationContext appContext : Context): SmartLocation {
        return SmartLocation.Builder(appContext)
                            .logging(true)
                            .build()
    }

    //////////////////////////////
    // NOTIFICATIONS
    //////////////////////////////

    @Provides
    @Singleton
    fun provideAppNotificationManager (@ApplicationContext appContext : Context) : AppNotificacionManager {
        return AppNotificacionManager(appContext = appContext, provideNotificationManager(appContext))
    }

    @Provides
    @Singleton
    fun provideNotificationManager (@ApplicationContext appContext : Context) : NotificationManager {
        return appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    // WEAR
    @Provides
    @Singleton
    fun provideWereableDataManager (@ApplicationContext appContext : Context) : DataClient {
        return Wearable.getDataClient(appContext)
    }
}