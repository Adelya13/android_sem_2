package kpfu.itis.valisheva.android_app.di

import android.content.Context
import dagger.Component
import dagger.Provides
import kpfu.itis.valisheva.android_app.di.modules.*
import kpfu.itis.valisheva.android_app.presentation.activities.MainActivity
import kpfu.itis.valisheva.android_app.presentation.fragments.CityFragment
import kpfu.itis.valisheva.android_app.presentation.fragments.FirstFragment


@Component(
    modules = [
        LocationModule::class,
        AppModule::class,
        MapperModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(firstFragment: FirstFragment)
    fun inject(cityFragment: CityFragment)
}
