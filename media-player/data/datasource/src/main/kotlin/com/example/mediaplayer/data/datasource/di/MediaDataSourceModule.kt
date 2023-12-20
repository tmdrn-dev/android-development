package com.example.mediaplayer.data.datasource.di

import android.content.Context
import com.example.mediaplayer.data.datasource.MediaDataSource
import com.example.mediaplayer.data.datasource.foo.MusicDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MediaDataSourceModule {
    @Singleton
    @Provides
    fun provideMusicDataSource(
        @ApplicationContext context: Context
    ): MediaDataSource = MusicDataSource(context)
}
