package com.example.datastorewithhilt.data.repository.di

import com.example.datastorewithhilt.data.repository.TimerRepository
import com.example.datastorewithhilt.data.repository.fake.FakeTimerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimerModule {
    @Provides
    @Singleton
    fun provideFakeTimeRepository(): TimerRepository = FakeTimerRepository()
}