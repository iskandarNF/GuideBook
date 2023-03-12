package tj.iskandar.guidebook.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import tj.iskandar.guidebook.utils.BaseResponse
import java.io.IOException

open class BaseUseCase {
    suspend fun <T : Any> safeFlowApiCall(
        call: suspend () -> Response<T>
    ): Flow<Resource<T>> {
        return flow {
            try {
                emit(Resource.Loading)
                val response = call()
                if (response.isSuccessful) emit(
                    Resource.Success(
                        response.body()!!,
                        response.code()
                    )
                )
                else emit(response.parseError())
            } catch (e: HttpException) {
                println(e.message)
                emit(Resource.NetworkError(e))
            } catch (e: IOException) {
                println(e.message)
                emit(Resource.NetworkError(e))
            }
        }
    }
}

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val content: T, val code: Int) : Resource<T>()
    data class Error(val message: String, val code: Int) : Resource<Nothing>()
    data class NetworkError(val exception: Exception, val code: Int = 0) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

@OptIn(ExperimentalStdlibApi::class)
fun <T : Any> Response<T>.parseError(): Resource<T> {
    return try {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<BaseResponse>()
        val response = jsonAdapter.fromJson(
            errorBody()?.string().toString()
        )

        println(response?.errMsg)

        Resource.Error(response?.errMsg ?: "", code())
    } catch (e: Exception) {
        println(e.message)
        Resource.NetworkError(e)
    }
}