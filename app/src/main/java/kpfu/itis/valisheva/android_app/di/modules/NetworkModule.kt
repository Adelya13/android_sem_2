package kpfu.itis.valisheva.android_app.di.modules

import dagger.Module
import dagger.Provides
import kpfu.itis.valisheva.android_app.BuildConfig
import kpfu.itis.valisheva.android_app.data.api.Api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"
private const val QUERY_API_KEY = "appid"

@Module
class NetworkModule {

    @Provides
    @Named("apiKey")
    fun apiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @Named("logger")
    fun provideLoggingInterceptor() : Interceptor{
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
    }

    @Provides
    fun okhttp(
        @Named("apiKey") apiKeyInterceptor: Interceptor,
        @Named("logger") httpLoggingInterceptor: Interceptor,

    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        httpLoggingInterceptor
                    )
                }
            }
            .build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun api(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(Api::class.java)

}
