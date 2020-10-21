package dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DBMembers

@Dao
abstract class MembersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMembers(members: DBMembers)

    @Query("SELECT * FROM members")
    abstract fun getRepositoryMembers(): PagingSource<Int, DBMembers>
}