package tj.iskandar.guidebook.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "endDate")
    val endDate: String? = "",
    @Json(name = "icon")
    val icon: String? = "",
    @Json(name = "loginRequired")
    val loginRequired: Boolean? = false,
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "objType")
    val objType: String? = "",
    @Json(name = "startDate")
    val startDate: String? = "",
    @Json(name = "url")
    val url: String? = ""
)