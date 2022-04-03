package kpfu.itis.valisheva.android_app.di.modules

import android.content.ContentProvider
import android.content.Context
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kpfu.itis.valisheva.android_app.App
import kpfu.itis.valisheva.android_app.presentation.fragments.FirstFragment

@Module
class AppModule {

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    fun bindContext(application: App): Context = application.applicationContext

}
