package com.github.members.baseplate_persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.members.baseplate_persistence.dao.MembersDao
import com.github.members.baseplate_persistence.dao.ProfileDao
import com.github.members.baseplate_persistence.dao.RemoteKeysDao
import com.github.members.baseplate_persistence.dao.SearchDao
import com.github.members.baseplate_persistence.model.DBMembers
import com.github.members.baseplate_persistence.model.DBProfiles
import com.github.members.baseplate_persistence.model.DBRemoteKeys
import com.github.members.baseplate_persistence.model.DBSearch

@Database(
    entities = [
        DBMembers::class,
        DBRemoteKeys::class,
        DBProfiles::class,
        DBSearch::class],
    version = 6,
    exportSchema = false
)

@TypeConverters
abstract class AppDatabase : RoomDatabase() {
    abstract fun membersDao(): MembersDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun profileDao(): ProfileDao
    abstract fun searchDao(): SearchDao
}
