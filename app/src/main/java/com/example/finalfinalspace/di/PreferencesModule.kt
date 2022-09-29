package com.example.finalfinalspace.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PreferencesModule {

    @Provides
    @Singleton
    fun providePreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("com.example.finalfinalspace_preferences", MODE_PRIVATE)
    }
}
