package kpfu.itis.valisheva.android_app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kpfu.itis.valisheva.android_app.di.ViewModelKey
import kpfu.itis.valisheva.android_app.presentation.viewmodels.CityModelView
import kpfu.itis.valisheva.android_app.presentation.viewmodels.FirstModelView
import kpfu.itis.valisheva.android_app.utils.WeatherViewModelFactory

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: WeatherViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FirstModelView::class)
    fun bindFirstViewModel(
        viewModel: FirstModelView
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityModelView::class)
    fun bindCityViewModel(
        viewModel: CityModelView
    ): ViewModel
}
