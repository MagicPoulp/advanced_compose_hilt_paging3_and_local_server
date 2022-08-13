package com.example.testsecuritythierry.viewmodels

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.*
import com.example.testsecuritythierry.repositories.PackageManagerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class UiState {
    object Empty : UiState()
    object InProgress : UiState()
    object Error : UiState()
    object Filled : UiState()
}

class ApplicationsInspectorViewModel(
    private val packageManagerRepository: PackageManagerRepository
) : ViewModel() {

    var listPackages: MutableLiveData<MutableList<PackageInfo>> = MutableLiveData<MutableList<PackageInfo>>()

    private val _uiState = MutableLiveData<UiState>(UiState.Empty)
    val uiState: LiveData<UiState>
        get() = _uiState

    fun init(owner: LifecycleOwner, packageManager: PackageManager) {
        listPackages.observe(owner) {
            //_uiState.value = UiState.Filled
        }
        getPackagesInLoop(owner, packageManager)
    }

    private fun getPackagesAsStrings(list: List<PackageInfo>) {
        list.map{ "(" + it.packageName + "-" + it.versionName + ")"}.sorted()
    }

    private fun getPackagesInLoop(owner: LifecycleOwner, packageManager: PackageManager){
        owner.lifecycleScope.launch {
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
}
