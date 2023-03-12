package tj.iskandar.guidebook.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.iskandar.guidebook.data.remote.Resource
import tj.iskandar.guidebook.db.AppDatabase
import tj.iskandar.guidebook.db.entity.GuideEntity
import tj.iskandar.guidebook.state.GuideBookUIState
import tj.iskandarbek.omdb.data.remote.GuideApi
import tj.iskandarbek.omdb.data.remote.GuideApiRepositoryImpl
import javax.inject.Inject
import androidx.room.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@HiltViewModel
class GuideBookViewModel @Inject constructor(
    private val db: AppDatabase,
    private val repositoryImpl: GuideApiRepositoryImpl,
    private val api:GuideApi
) : ViewModel() {

    var bookList: GuideBookUIState by mutableStateOf(GuideBookUIState())


    var currentItems: List<GuideEntity> = emptyList()
    var totalCount: Int = 0
    var offset: Int = 0

    val items = MutableStateFlow(currentItems)
    val isLoading = MutableStateFlow(false)
    val hasMoreItems: Boolean
        get() = currentItems.size < totalCount


    init {
        getBooks()
        loadMoreItems()
    }
    fun loadMoreItems() {
        if (isLoading.value || !hasMoreItems) {
            return
        }

        isLoading.value = true

        viewModelScope.launch {
            try {
                val items = db.postDao().getItems(offset, PAGE_SIZE)
                currentItems = currentItems + items
                offset += PAGE_SIZE
                totalCount = db.postDao().getCount()
                this@GuideBookViewModel.items.value = currentItems
            } catch (e: Exception) {
                // Handle the error
            } finally {
                isLoading.value = false
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 3
    }

     fun getBooks() {
        viewModelScope.launch {
            repositoryImpl.getBooks(

            ).collect { result ->
                bookList = when (result) {
                    is Resource.Success -> {
                        fetchPosts()
                        GuideBookUIState(data = result.content)

                    }
                    is Resource.Loading -> {
                        GuideBookUIState(isLoading = true)
                    }
                    is Resource.Error -> {
                        GuideBookUIState(error = result.message)
                    }
                    is Resource.NetworkError -> {
                        GuideBookUIState(error = result.exception.toString())
                    }
                }
            }

        }

    }

    fun fetchPosts() {
        viewModelScope.launch {
            val posts = api.getBooks().body()?.data
                ?.map { GuideEntity( it?.endDate?:"", it?.icon?:"",it?.loginRequired?:false,
                    it?.name?:"", it?.objType?:"", it?.startDate?:"", it?.url?:"") }

            db.postDao().insertAll(posts!!)
        }
    }

    val posts: LiveData<List<GuideEntity>> = liveData {
        emitSource(db.postDao().getAll())
    }

}