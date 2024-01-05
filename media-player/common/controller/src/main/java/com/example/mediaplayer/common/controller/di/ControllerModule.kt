package com.example.mediaplayer.common.controller.di;

import android.content.Context;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import com.example.mediaplayer.common.controller.MediaPlayerController;

@Module
@InstallIn(SingletonComponent::class)
class ControllerModule {

    @Provides
    @Singleton
    fun provideMediaController(
        @ApplicationContext context:Context
    ): MediaPlayerController = MediaPlayerController(context)
}
