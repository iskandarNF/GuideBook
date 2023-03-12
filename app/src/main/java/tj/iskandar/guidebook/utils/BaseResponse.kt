package tj.iskandar.guidebook.utils

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse (
    @Json(name = "Response")
    val errMsg: String?="",
    @Json(name = "Error")
    val errCode: Int?=0,
)