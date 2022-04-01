package kpfu.itis.valisheva.android_app

import android.app.Application
import kpfu.itis.valisheva.android_app.di.AppComponent
import kpfu.itis.valisheva.android_app.di.modules.AppModule

import kpfu.itis.valisheva.android_app.di.DaggerAppComponent
import kpfu.itis.valisheva.android_app.di.modules.LocationModule
import kpfu.itis.valisheva.android_app.di.modules.MapperModule
import kpfu.itis.valisheva.android_app.di.modules.NetworkModule


class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .networkModule(NetworkModule())
            .locationModule(LocationModule())
            .mapperModule(MapperModule())
            .build()
    }
}
