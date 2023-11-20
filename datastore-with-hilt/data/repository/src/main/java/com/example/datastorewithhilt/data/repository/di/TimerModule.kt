package com.example.datastorewithhilt.data.repository.di

import com.example.datastorewithhilt.data.repository.TimerRepository
import com.example.datastorewithhilt.data.repository.fake.TimerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TimerModule {

    @Provides
    fun provideFakeTimeRepository(): TimerRepository = TimerRepositoryImpl()
}