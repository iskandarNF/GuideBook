package tj.iskandarbek.omdb.data.remote

import kotlinx.coroutines.flow.Flow
import tj.iskandar.guidebook.data.model.GuideBookModel
import tj.iskandar.guidebook.data.remote.BaseUseCase
import tj.iskandar.guidebook.data.remote.Resource
import javax.inject.Inject

class GuideApiRepositoryImpl @Inject constructor(
    private val guideApi: GuideApi
) : GuideApiRepository, BaseUseCase() {

    override suspend fun getBooks(
    ): Flow<Resource<GuideBookModel>> =
        safeFlowApiCall {
            guideApi.getBooks()
        }

}