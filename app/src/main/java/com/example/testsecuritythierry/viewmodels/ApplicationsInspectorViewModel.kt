package com.example.testsecuritythierry.viewmodels

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.*
import com.example.testsecuritythierry.config.hashOfVirus1
import com.example.testsecuritythierry.config.manuallyAddAVirus
import com.example.testsecuritythierry.config.virus1
import com.example.testsecuritythierry.models.AnalysisResult
import com.example.testsecuritythierry.models.AnalysisResultPending
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

    // LiveData are observed in the UI
    var listPackages: MutableLiveData<MutableList<PackageInfo>> = MutableLiveData<MutableList<PackageInfo>>()
    var mapAppToVirusStatus: MutableMap<String, MutableLiveData<AnalysisResult>> = mutableMapOf()
    var mapHashToVirusStatusHistory: MutableMap<String, MutableLiveData<AnalysisResult>> = mutableMapOf()

    var listPackagesAsStrings: List<String> = emptyList()
    private var mapAppToHash: MutableMap<String, String> = mutableMapOf()
    var mapHashToApp: MutableMap<String, String> = mutableMapOf()
    var initialized = false

    private val _uiState = MutableLiveData<UiState>(UiState.Empty)
    val uiState: LiveData<UiState>
        get() = _uiState

    fun init(virusTotalRawApiKey: String, owner: LifecycleOwner, packageManager: PackageManager) {
        if (initialized) {
            return
        }
        initialized = true
        listPackages.observe(owner) { allPackages ->
            allPackages.toList().forEach { packageInfo ->
                mapAppToVirusStatus[packageInfo.packageName] = MutableLiveData(AnalysisResultPending())
            }
            _uiState.value = UiState.Filled
            GlobalScope.launch {
                mapAppToHash = computePackagesHashes(allPackages.toList())
                mapHashToApp = mapAppToHash.entries.associateBy({ it.value }) { it.key }.toMutableMap()
                val flowAnalyze = virusCheckerRepository.analyseFileHashes(mapAppToHash.values.toList())
                flowAnalyze.collect { (hash, v) -> run {
                    val app: String? = mapHashToApp[hash]
                    app?.let {
                        mapAppToVirusStatus[app]?.let {
                            mapAppToVirusStatus[app]?.postValue(v as AnalysisResult)
                        } ?: run {
                            mapAppToVirusStatus[app] = MutableLiveData(v as AnalysisResult)
                        }
                    }
                }}
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

    private fun computePackagesHashes(list: List<PackageInfo>): MutableMap<String, String> {
        val result: MutableMap<String, String> = mutableMapOf()
        list.map {
            if (manuallyAddAVirus && it.packageName == virus1) {
                result[it.packageName] = hashOfVirus1
            } else {
                val urlPath = it.applicationInfo.sourceDir
                val file = File(urlPath)
                val md5 = MD5.calculateMD5(file)
                result[it.packageName] = md5
            }
        }
        return result
    }

}
