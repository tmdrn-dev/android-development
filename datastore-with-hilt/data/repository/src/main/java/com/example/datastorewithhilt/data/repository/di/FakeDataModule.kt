package com.example.datastorewithhilt.data.repository.di

import com.example.datastorewithhilt.data.repository.DataRepository
import com.example.datastorewithhilt.data.repository.fake.FakeDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FakeDataModule {
    @Binds
    @Singleton
    fun bindsFakeDataRepository(
        fakeDataRepository: FakeDataRepository,
    ): DataRepository
}