package kpfu.itis.valisheva.android_app.di.modules

import android.content.ContentProvider
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    fun provideContext(): Context = Fragment().requireContext()

}
