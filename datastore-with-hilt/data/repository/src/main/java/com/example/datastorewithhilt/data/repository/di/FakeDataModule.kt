package com.example.datastorewithhilt.data.repository.di

import com.example.datasource.FakeDataSource
import com.example.datastorewithhilt.data.repository.DataRepository
import com.example.datastorewithhilt.data.repository.UserDataRepository
import com.example.datastorewithhilt.data.repository.fake.FakeDataRepository
import com.example.datastorewithhilt.data.repository.fake.FakeUserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface FakeDataModule {
    @Singleton
    @Binds
    fun bindsFakeDataRepository(
        fakeDataRepository: FakeDataRepository,
    ): DataRepository
}