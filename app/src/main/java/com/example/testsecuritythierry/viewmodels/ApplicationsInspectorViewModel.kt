package com.example.testsecuritythierry.viewmodels

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.*
import com.example.testsecuritythierry.repositories.MD5
import com.example.testsecuritythierry.repositories.PackageManagerRepository
import com.example.testsecuritythierry.repositories.VirusCheckerRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

sealed class UiState {
    object Empty : UiState()
    object InProgress : UiState()
    object Error : UiState()
    object Filled : UiState()
}

class ApplicationsInspectorViewModel(
    private val packageManagerRepository: PackageManagerRepository,
    private val virusCheckerRepository: VirusCheckerRepository
) : ViewModel() {

    var listPackages: MutableLiveData<MutableList<PackageInfo>> = MutableLiveData<MutableList<PackageInfo>>()
    var listPackagesAsStrings: List<String> = emptyList()
    var listCheckSums: List<String> = emptyList()
    var analyzedFilesMap: Map<String, Any> = emptyMap()

    private val _uiState = MutableLiveData<UiState>(UiState.Empty)
    val uiState: LiveData<UiState>
        get() = _uiState

    fun init(virusTotalRawApiKey: String, owner: LifecycleOwner, packageManager: PackageManager) {
        listPackages.observe(owner) {
            _uiState.value = UiState.Filled
            GlobalScope.launch {
                listCheckSums = computePackagesHashes(it.toList())
               // analyzedFilesMap = virusCheckerRepository.analyseFileHashes(listCheckSums)
            }
        }
        virusCheckerRepository.init(
            virusTotalRawApiKeyIn = virusTotalRawApiKey
        )
        getPackagesInLoop(packageManager)
    }

    private fun getPackagesAsStrings(list: List<PackageInfo>): List<String> {
        return list.map{ "(" + it.packageName + "-" + it.versionName + ")"}.sorted()
    }

    // every 30 seconds, we try to update the list of packages
    private fun getPackagesInLoop(packageManager: PackageManager){
        viewModelScope.launch {
            while (true) {
                packageManagerRepository.getPackages(packageManager = packageManager).collect { list ->
                    val newListShort = getPackagesAsStrings(list)
                    if (newListShort != listPackagesAsStrings) {
                        _uiState.value = UiState.InProgress
                        listPackagesAsStrings = newListShort
                        listPackages.postValue(list)
                    }
                }
                delay(30*1000L)
            }
        }
    }

    private fun computePackagesHashes(list: List<PackageInfo>): List<String> {
        val listFiles = list.map { it.applicationInfo.sourceDir }
        val listCheckSums = listFiles.map { urlPath ->
            val file = File(urlPath)
            val result = MD5.calculateMD5(file)
            result
        }
        return listCheckSums
    }

}
