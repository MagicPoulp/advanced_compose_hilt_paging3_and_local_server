package com.example.testsecuritythierry.viewmodels


import androidx.lifecycle.*
import com.example.testsecuritythierry.config.analysisRefreshInterval
import com.example.testsecuritythierry.models.DataNewsElement

import com.example.testsecuritythierry.repositories.NewsDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
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
    var listNews: MutableLiveData<MutableList<DataNewsElement>> = MutableLiveData<MutableList<DataNewsElement>>()
    // The UI state for showing the first page with a spinner or not
    private val _uiState = MutableLiveData<UiState>(UiState.Empty)
    val uiState: LiveData<UiState>
        get() = _uiState


    // ------------------------------------------
    // non LiveData variables
    var initialized = false

    // an init function is required to pass the string resource and the Activity lifecycle owner
    fun init(owner: LifecycleOwner) {
        if (initialized) {
            return
        }
        initialized = true

        // any update on the list of packages is observed
        listNews.observe(owner) { // allNews ->
            /*
            // The UI has one status indicator for each package
            // set all statuses as pending
            mapAppToVirusStatus = mutableMapOf()
            allPackages.toList().forEach { packageInfo ->
                mapAppToVirusStatus[packageInfo.packageName]?.let {
                    it.postValue(AnalysisResultPending())
                } ?: run {
                    // we create the observables only if they do not exist
                    mapAppToVirusStatus[packageInfo.packageName] =
                        MutableLiveData(AnalysisResultPending())
                }
            }
            // reset counters
            _numUnfinished.value = allPackages.size
            _numViruses.value = 0
            // this triggers a display of the first list of packages
            _uiState.value = UiState.Filled
            */

            _uiState.value = UiState.Filled
        }
        getNewsInLoop()
    }

    // every 30 seconds, we try to update the list of packages
    private fun getNewsInLoop(){
        viewModelScope.launch {
            //while (true) {
                newsDataRepository.getNews().collect { list ->
                    listNews.postValue(list.toMutableList())
                }
            //    delay(analysisRefreshInterval)
            //}
        }
    }
}
