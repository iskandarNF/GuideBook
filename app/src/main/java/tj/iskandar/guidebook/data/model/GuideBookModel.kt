package tj.iskandar.guidebook.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GuideBookModel(
    @Json(name = "data")
    val `data`: List<Data?>? = listOf(),
    @Json(name = "total")
    val total: String? = ""
)