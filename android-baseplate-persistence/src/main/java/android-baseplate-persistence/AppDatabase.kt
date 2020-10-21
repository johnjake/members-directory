import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dao.MembersDao
import model.DBMembers

@Database(
    entities = [DBMembers::class],
    version = 1,
    exportSchema = false
)

@TypeConverters
abstract class AppDatabase : RoomDatabase() {
    abstract fun membersDao(): MembersDao
}
