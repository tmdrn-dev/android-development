package com.example.datastorewithhilt.data.repository.di

import com.example.datastorewithhilt.data.repository.TimerRepository
import com.example.datastorewithhilt.data.repository.UserDataRepository
import com.example.datastorewithhilt.data.repository.fake.FakeUserDataRepository
import com.example.datastorewithhilt.data.repository.fake.TimerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindsUserDataRepository(
        userDataRepository: FakeUserDataRepository,
    ): UserDataRepository

//    @Binds
//    fun bindsTimerRepository(
//        timerRepository: TimerRepositoryImpl,
//    ): TimerRepository
}