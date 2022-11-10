package com.gvelesiani.socialx.hiltModules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {
    @Provides
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.applicationContext.getSharedPreferences(
            "SocialX_preferences",
            Context.MODE_PRIVATE
        )
    }
}