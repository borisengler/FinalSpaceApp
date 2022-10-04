package com.example.finalfinalspace.di

import com.example.finalfinalspace.di.qualifiers.WelcomeMessage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object WelcomeModule {

    @WelcomeMessage
    @Provides
    fun provideWelcomeMessage(): String = "Welcome! This is paid welcome message"
}
