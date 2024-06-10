package com.example.f1livetiming.data.di

import com.example.f1livetiming.data.repository.F1LiveTimingRepository
import com.example.f1livetiming.data.repository.F1LiveTimingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindF1LiveTimingRepository(
        f1LiveTimingRepositoryImpl: F1LiveTimingRepositoryImpl
    ): F1LiveTimingRepository

}