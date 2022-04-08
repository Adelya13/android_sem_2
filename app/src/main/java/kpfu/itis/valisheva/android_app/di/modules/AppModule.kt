package kpfu.itis.valisheva.android_app.di.modules

import android.content.ContentProvider
import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kpfu.itis.valisheva.android_app.App
import kpfu.itis.valisheva.android_app.presentation.fragments.FirstFragment

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideFusedClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

//    @Provides
//    fun bindContext(application: App): Context = application.applicationContext

}
