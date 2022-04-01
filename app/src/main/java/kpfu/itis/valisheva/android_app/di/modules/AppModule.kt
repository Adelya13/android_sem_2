package kpfu.itis.valisheva.android_app.di.modules

import android.content.ContentProvider
import android.content.Context
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class AppModule {

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}
