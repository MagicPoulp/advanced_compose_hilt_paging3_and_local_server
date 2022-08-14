package com.example.testsecuritythierry.viewmodels

import android.app.Activity
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.*
import com.example.testsecuritythierry.R
import com.example.testsecuritythierry.http.DataVirusTotalFile
import com.example.testsecuritythierry.repositories.MD5
import com.example.testsecuritythierry.repositories.PackageManagerRepository
import com.example.testsecuritythierry.repositories.VirusCheckerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI
import java.nio.file.Paths

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
    var listCheckSums: List<String> = emptyList()
    var analyzedFilesMap: Map<String, Any> = emptyMap()

    private val _uiState = MutableLiveData<UiState>(UiState.Empty)
    val uiState: LiveData<UiState>
        get() = _uiState

    fun init(virusTotalRawApiKey: String, owner: LifecycleOwner, packageManager: PackageManager) {
        listPackages.observe(owner) {
            //_uiState.value = UiState.Filled
            listCheckSums = computePackagesHashes(it.toList()).take(80)
            viewModelScope.launch {
                analyzedFilesMap = virusCheckerRepository.analyseFileHashes(listCheckSums)
            }
        }
        virusCheckerRepository.init(
            virusTotalRawApiKeyIn = virusTotalRawApiKey
        )
        getPackagesInLoop(packageManager)
    }

    private fun getPackagesAsStrings(list: List<PackageInfo>) {
        list.map{ "(" + it.packageName + "-" + it.versionName + ")"}.sorted()
    }

    private fun getPackagesInLoop(packageManager: PackageManager){
        viewModelScope.launch {
            while (true) {
                packageManagerRepository.getPackages(packageManager = packageManager).collect { list ->
                    val listShort = getPackagesAsStrings(list)
                    if (listShort != listPackages.value?.let { getPackagesAsStrings(it.toList()) }) {
                        // we update only if a new package or a new version is detected
                        listPackages.postValue(list)
                    }
                }
                delay(60*1000L)
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
