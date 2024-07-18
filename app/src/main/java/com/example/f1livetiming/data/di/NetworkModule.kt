package com.example.f1livetiming.data.di

import com.example.f1livetiming.data.network.F1Service
import com.example.f1livetiming.data.network.converter.EmptyConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.openf1.org/v1/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }


    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(EmptyConverter())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun providesF1Service(retrofit: Retrofit): F1Service{
        return retrofit.create(F1Service::class.java)
    }

}