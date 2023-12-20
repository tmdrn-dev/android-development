package com.example.mediaplayer.data.repository.di

import com.example.mediaplayer.data.repository.MediaRepository
import com.example.mediaplayer.data.repository.foo.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface MediaRepositoryModule {
    @Singleton
    @Binds
    fun bindsMusicRepository(
        mediaRepository: MusicRepository,
    ): MediaRepository
}
