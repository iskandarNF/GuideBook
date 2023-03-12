package tj.iskandarbek.omdb.data.remote

import retrofit2.Response
import retrofit2.http.GET
import tj.iskandar.guidebook.data.model.GuideBookModel


interface GuideApi {
    @GET("service/v2/upcomingGuides/")
    suspend fun getBooks(
    ): Response<GuideBookModel>

}