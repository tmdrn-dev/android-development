package com.example.datasource.di

import com.example.datasource.FakeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FakeDataSourceModule {
    @Singleton
    @Provides
    fun providePreferencesDataStore(): FakeDataSource {
        return FakeDataSource()
    }
}