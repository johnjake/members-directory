package com.github.members.directory.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.github.members.directory.features.users.UsersFragment
import org.koin.dsl.module

val storageModule = module {
    single { providesSharedPreferences(get()) }
    single { providesSharedPrefTheme(get()) }
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