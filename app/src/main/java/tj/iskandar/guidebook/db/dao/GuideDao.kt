package tj.iskandar.guidebook.db.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tj.iskandar.guidebook.db.entity.GuideEntity

@Dao
interface GuideDao {
    @Query("SELECT * FROM books")
    fun getAll(): LiveData<List<GuideEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<GuideEntity>)

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getCount(): Int

    @Query("SELECT * FROM books LIMIT :limit OFFSET :offset")
    suspend fun getItems(offset: Int, limit: Int): List<GuideEntity>
}