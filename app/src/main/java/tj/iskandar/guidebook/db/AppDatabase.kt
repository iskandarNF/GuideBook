package tj.iskandar.guidebook.db


import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Provides
import tj.iskandar.guidebook.db.dao.GuideDao
import tj.iskandar.guidebook.db.entity.GuideEntity
import javax.inject.Singleton

@Database(entities = [GuideEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): GuideDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
