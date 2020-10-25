package com.github.members.baseplate_persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.members.baseplate_persistence.model.DBSearch

@Dao
abstract class SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSearchDao(searchUser: DBSearch)

    @Query("SELECT * FROM table_search GROUP BY login")
    abstract suspend fun getUserSearch(): List<DBSearch>
}