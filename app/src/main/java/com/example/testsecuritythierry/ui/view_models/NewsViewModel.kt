package com.example.testsecuritythierry.ui.view_models


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testsecuritythierry.data.models.DataNewsElement
import com.example.testsecuritythierry.data.repositories.NewsDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Empty : UiState()
    object InProgress : UiState()
    object Error : UiState()
    object Filled : UiState()
}

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsDataRepository: NewsDataRepository,
) : ViewModel() {

    // LiveData are observed in the UI
    // the underscore is used to limit the access from outside this file

    // the list of packages installed on the device
    var listNews: MutableStateFlow<List<DataNewsElement>> = MutableStateFlow(emptyList())
    // The UI state for showing the first page with a spinner or not
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)

    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()


    // ------------------------------------------
    // non LiveData variables
    private var initialized = false

    // an init function is required to pass the string resource and the Activity lifecycle owner
    fun init(owner: LifecycleOwner) {
        if (initialized) {
            return
        }
        initialized = true

        owner.lifecycleScope.launch {
            owner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                listNews.collect {
                    val newState = when (it.size) {
                        0 -> UiState.Empty
                        else -> UiState.Filled
                    }
                    _uiState.emit(newState)
                }
            }
        }
        getNewsInLoop(owner)
    }

    private fun getNewsInLoop(owner: LifecycleOwner) {
        owner.lifecycleScope.launch {
            newsDataRepository.getNews().collect { list ->
                listNews.emit(list)
            }
        }
    }
}
