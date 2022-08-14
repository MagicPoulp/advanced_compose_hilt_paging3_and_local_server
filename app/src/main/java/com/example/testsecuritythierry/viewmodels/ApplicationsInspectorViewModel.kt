package com.example.testsecuritythierry.viewmodels

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.*
import com.example.testsecuritythierry.config.analysisRefreshInterval
import com.example.testsecuritythierry.config.hashOfVirus1
import com.example.testsecuritythierry.config.manuallyAddAVirus
import com.example.testsecuritythierry.config.virus1
import com.example.testsecuritythierry.models.AnalysisResult
import com.example.testsecuritythierry.models.AnalysisResultError
import com.example.testsecuritythierry.models.AnalysisResultPending
import com.example.testsecuritythierry.repositories.MD5
import com.example.testsecuritythierry.repositories.PackageManagerRepository
import com.example.testsecuritythierry.repositories.VirusCheckerRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
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
    private var mapHashToVirusStatusHistory: MutableMap<String, AnalysisResult> = mutableMapOf()

    var listPackagesAsStrings: List<String> = emptyList()
    private var mapAppToHash: MutableMap<String, String> = mutableMapOf()
    var mapHashToApp: MutableMap<String, String> = mutableMapOf()
    var initialized = false
    var analysisJob: Job? = null

    private val _uiState = MutableLiveData<UiState>(UiState.Empty)
    val uiState: LiveData<UiState>
        get() = _uiState

    // an init function is required to pass the string resource and the Activity lifecycle owner
    fun init(virusTotalRawApiKey: String, owner: LifecycleOwner, packageManager: PackageManager) {
        if (initialized) {
            return
        }
        initialized = true

        // any update on the list of packages is observed
        listPackages.observe(owner) { allPackages ->
            // The UI has one status indicator for each package
            // set all statuses as pending
            mapAppToVirusStatus = mutableMapOf()
            allPackages.toList().forEach { packageInfo ->
                mapAppToVirusStatus[packageInfo.packageName] = MutableLiveData(AnalysisResultPending())
            }
            // this triggers a display of the first list of packages
            _uiState.value = UiState.Filled
            analyzeAllPackages(allPackages)
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
                    } else {
                        // we wait for all pending statuses to be finished
                        val pending = mapAppToVirusStatus.values.map { it.value }.filterIsInstance<AnalysisResultPending>()
                        val errors = mapAppToVirusStatus.values.map { it.value }.filterIsInstance<AnalysisResultError>()
                        if (errors.isNotEmpty() && pending.isEmpty()) {
                            listPackages.postValue(list)
                        }
                    }
                }
                delay(analysisRefreshInterval)
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

    // mapAppToVirusStatus[app] will get the result of the analysis and update the UI
    private fun analyzeAllPackages(allPackages: List<PackageInfo>) {
        analysisJob?.let {
            // we cancel the job but not the collect (or we would use flowAnalyze.cancellable())
            // we interrupt to start a new analysis, and it can affect the quota if requests are already sent.
            // it can result in errors. But it does not matter because we refresh errors
            // since we waited for all Pending statuses to have disappeared, it should not interrupt anything
            analysisJob?.cancel()
        }
        // we must use the global scope so that we do not block the UI
        analysisJob = GlobalScope.launch {
            mapAppToHash = computePackagesHashes(allPackages.toList())
            mapHashToApp = mapAppToHash.entries.associateBy({ it.value }) { it.key }.toMutableMap()
            val flowAnalyze = virusCheckerRepository.analyseFileHashes(
                hashes = mapAppToHash.values.toList(),
                mapHashToVirusStatusHistory = mapHashToVirusStatusHistory)
            flowAnalyze.collect { (hash, v) -> run {
                // when collecting results, we set mapAppToVirusStatus[app]
                // and it will update the right column in the UI
                val app: String? = mapHashToApp[hash]
                app?.let {
                    // if the value was being collected while a new analysis was sent for the job
                    // we do nothing, because we have resetted all values as pending

                    // we update the UI with the result
                    mapAppToVirusStatus[app]?.let {
                        if (it.value as AnalysisResult != v as AnalysisResult) {
                            mapAppToVirusStatus[app]?.postValue(v as AnalysisResult)
                        }
                    } ?: run {
                        mapAppToVirusStatus[app] = MutableLiveData(v as AnalysisResult)
                    }
                    if (v !is AnalysisResultError) {
                        mapHashToVirusStatusHistory[hash] = v as AnalysisResult
                    }
                }
            }}
        }
    }
}
