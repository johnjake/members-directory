package com.github.members.directory

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.github.members.directory.di.networkModule
import com.github.members.directory.di.repositoryModule
import com.github.members.directory.di.storageModule
import com.github.members.directory.di.viewModelModule
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
                repositoryModule,
                viewModelModule
            ))
        }
    }
}