package com.gaurav.weatherapp.dagger

import android.app.Application
import android.content.Context
import com.gaurav.gojekassignment.util.APIConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    fun providesAppContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    internal fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRetrofitApi(rxJava2CallAdapterFactory: RxJava2CallAdapterFactory, converter: Converter.Factory, @Named(
        APIConstants.BASE_URL) baseUrl: String, okHttpClient: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient.build())
                .build()
    }

}