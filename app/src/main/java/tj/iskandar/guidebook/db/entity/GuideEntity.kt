package tj.iskandar.guidebook.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "books")
data class GuideEntity(
    val endDate: String,
    val icon: String,
    val loginRequired: Boolean,
    val name: String,
    val objType: String,
    val startDate: String,
    @PrimaryKey @ColumnInfo(name = "url") val url: String
)
