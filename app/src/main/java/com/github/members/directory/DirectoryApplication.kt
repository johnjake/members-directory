package com.github.members.directory

import android.app.Application
import com.github.members.directory.di.networkModule
import com.github.members.directory.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DirectoryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DirectoryApplication)
            modules(listOf(
                networkModule,
                storageModule
            ))
        }
    }
}