package com.github.members.directory.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.koin.dsl.module

val storageModule = module {
    single { providesSharedPreferences(get()) }
}

fun providesSharedPreferences(application: Application): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
}