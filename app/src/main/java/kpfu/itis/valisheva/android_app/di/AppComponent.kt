package kpfu.itis.valisheva.android_app.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import kpfu.itis.valisheva.android_app.App
import kpfu.itis.valisheva.android_app.di.modules.*
import kpfu.itis.valisheva.android_app.presentation.activities.MainActivity
import kpfu.itis.valisheva.android_app.presentation.fragments.CityFragment
import kpfu.itis.valisheva.android_app.presentation.fragments.FirstFragment


@Component(
    modules = [
        //LocationModule::class,
        AppModule::class,
        MapperModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun provideApp(): Context

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }



    fun inject(firstFragment: FirstFragment)
    fun inject(cityFragment: CityFragment)


}
