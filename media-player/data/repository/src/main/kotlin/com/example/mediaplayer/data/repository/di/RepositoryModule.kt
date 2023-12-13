package com.example.mediaplayer.data.repository.di

import com.example.mediaplayer.data.repository.Repository
import com.example.mediaplayer.data.repository.foo.FooRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindsFooRepository(
        fooRepository: FooRepository,
    ): Repository
}
