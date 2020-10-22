package com.github.members.baseplate_persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.members.baseplate_persistence.dao.MembersDao
import com.github.members.baseplate_persistence.dao.RemoteKeysDao
import com.github.members.baseplate_persistence.model.DBMembers


@Database(
    entities = [DBMembers::class],
    version = 2,
    exportSchema = false
)

@TypeConverters
abstract class AppDatabase : RoomDatabase() {
    abstract fun membersDao(): MembersDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
