package com.github.members.baseplate_persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.members.baseplate_persistence.model.DBProfiles

@Dao
abstract class ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertProfileDao(profileDao: DBProfiles)

    @Query("SELECT * FROM table_profiles WHERE login = :userName OR login is null")
    abstract suspend fun getUserProfile(userName: String): DBProfiles
 }