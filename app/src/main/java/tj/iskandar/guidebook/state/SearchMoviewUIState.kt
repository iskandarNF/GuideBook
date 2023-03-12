package tj.iskandar.guidebook.state

import tj.iskandar.guidebook.data.model.GuideBookModel


data class GuideBookUIState(
    val data: GuideBookModel?=null,
    val isLoading: Boolean = false,
    val error: String?=null,
    val networkError: String?=null
)
