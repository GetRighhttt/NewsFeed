package com.example.newsfeed.presentation.di

import com.example.newsfeed.data.api.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
With dagger hilt, we already have components created for us, so there's
no need for components in the di package layer here.

All we need to do is install in. Hilt modules must be annotated with
@InstallIn to tell Hilt which Android class each module will be used or
installed in.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    /*
    Get a retrofit instance.
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        fun provideHttpInterceptor(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
            }.build()
            return client
        }

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.newscatcherapi.com/")
            .client(provideHttpInterceptor())
            .build()
    }

    /*
    Return an instance of NewsApiService.
     */
    @Singleton
    @Provides
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}