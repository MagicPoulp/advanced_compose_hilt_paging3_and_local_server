package com.example.testsecuritythierry.ui.view_models


import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testsecuritythierry.data.config.AppConfig
import com.example.testsecuritythierry.data.models.DataNewsElement
import com.example.testsecuritythierry.data.repositories.LocalNewsDataRepository
import com.example.testsecuritythierry.data.repositories.NewsDataPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class UiState {
    object Empty : UiState()
    object InProgress : UiState()
    object Error : UiState()
    object Filled : UiState()
}

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val localNewsDataRepository: LocalNewsDataRepository,
) : ViewModel() {
    // the list of packages installed on the device
    lateinit var listNews: Flow<PagingData<DataNewsElement>>
    // The UI state for showing the first page with a spinner or not
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)

    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    // ------------------------------------------
    // non flow variables
    private var initialized = false

    fun init(unexpectedServerDataErrorString: String) {
        if (initialized) {
            return
        }
        initialized = true

        // one can add a RemoteMediator for caching
        // https://developer.android.com/topic/libraries/architecture/paging/v3-network-db
        listNews = Pager(PagingConfig(pageSize = AppConfig.pagingSize)) {
            NewsDataPagingSource(unexpectedServerDataErrorString, localNewsDataRepository)
        }.flow
    }

    suspend fun setUiState(newUiState: UiState) {
        _uiState.emit(newUiState)
    }
}
