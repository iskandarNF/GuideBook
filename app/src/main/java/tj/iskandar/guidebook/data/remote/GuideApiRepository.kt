package tj.iskandarbek.omdb.data.remote

import kotlinx.coroutines.flow.Flow
import tj.iskandar.guidebook.data.model.GuideBookModel
import tj.iskandar.guidebook.data.remote.Resource


interface GuideApiRepository {
    suspend fun getBooks(
    ): Flow<Resource<GuideBookModel>>
}