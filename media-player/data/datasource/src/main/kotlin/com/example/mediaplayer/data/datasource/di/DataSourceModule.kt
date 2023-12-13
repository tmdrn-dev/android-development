package com.example.mediaplayer.data.datasource.di

import com.example.mediaplayer.data.datasource.foo.FooDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
    @Singleton
    @Provides
    fun provideFooDataSource(): FooDataSource = FooDataSource()
}
