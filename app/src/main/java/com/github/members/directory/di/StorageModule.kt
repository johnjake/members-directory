package com.github.members.directory.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.github.members.directory.features.splash.SplashActivity
import com.github.members.directory.features.users.UsersFragment
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module(override = true) {
    single { providesSharedPreferences(get()) }
    single { providesSharedPrefTheme(get()) }
    single { providesSharedOnline(get()) }
    single { providesSaveInternetStatePref(context = get(), storage = get()) }
}

fun providesSharedPreferences(application: Application): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
}

fun providesSharedPrefTheme(context: Context): Boolean? {
    val pref = context.getSharedPreferences(UsersFragment.SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
    )
    return pref?.getBoolean(UsersFragment.DARK_MODE, false)
}

fun providesSharedOnline(context: Context): Boolean? {
    val shardPreferencesKey = "myData"
    val localSharedKey = "members.directory.room"
    val pref = context.getSharedPreferences(shardPreferencesKey,
            AppCompatActivity.MODE_PRIVATE
    )
    return pref?.getBoolean(localSharedKey, false)
}

fun providesSaveInternetStatePref(context: Context, storage: Boolean) {
    val pref = context.getSharedPreferences(SplashActivity.SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
    )
    val editor = pref?.edit()
    editor?.putBoolean(SplashActivity.PERSIST_LOCAL, storage)
    editor?.apply()
}