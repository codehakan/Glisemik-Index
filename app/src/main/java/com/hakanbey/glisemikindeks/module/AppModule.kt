package com.hakanbey.glisemikindeks.module

import android.content.Context
import com.hakanbey.glisemikindeks.service.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 03.02.2022               │
//└──────────────────────────┘

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun injectDatabase(@ApplicationContext context: Context): Database {
        return Database(context)
    }


}