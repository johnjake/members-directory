package com.github.members.directory

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.github.members.directory.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DirectoryApplication : Application() {
    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DirectoryApplication)
            modules(listOf(
                networkModule,
                storageModule,
                databaseModule,
                mediatorModule,
                repositoryModule,
                viewModelModule
            ))
        }
    }
}