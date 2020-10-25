package com.github.members.directory.di

import android.app.Application
import androidx.room.Room
import com.github.members.baseplate_persistence.AppDatabase
import com.github.members.baseplate_persistence.dao.MembersDao
import com.github.members.baseplate_persistence.dao.RemoteKeysDao
import com.github.members.baseplate_persistence.dao.SearchDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "db_members")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun providesMembersDao(database: AppDatabase): MembersDao {
        return database.membersDao()
    }

    fun providesRemoteDao(database: AppDatabase): RemoteKeysDao {
        return database.remoteKeysDao()
    }

    fun providesSearchDao(database: AppDatabase): SearchDao {
        return database.searchDao()
    }

    single { provideDatabase(androidApplication()) }
    single { providesMembersDao(get()) }
    single { providesRemoteDao(get()) }
    single { providesSearchDao(get()) }
}