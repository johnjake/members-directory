package com.github.members.baseplate_persistence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.members.baseplate_persistence.model.DBMembers

@Dao
abstract class MembersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMembers(members: DBMembers)

    @Query("DELETE FROM members")
    abstract suspend fun clearAllKeys()

    @Query("SELECT * FROM members")
    abstract fun getRepositoryMembers(): PagingSource<Int, DBMembers>
}