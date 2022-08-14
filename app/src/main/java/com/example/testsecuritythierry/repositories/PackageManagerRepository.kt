package com.example.testsecuritythierry.repositories

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.example.testsecuritythierry.config.fivePackagesOnly
import com.example.testsecuritythierry.config.manuallyAddAVirus
import com.example.testsecuritythierry.config.virus1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class PackageManagerRepository: KoinComponent {

    suspend fun getPackages(packageManager: PackageManager): Flow<MutableList<PackageInfo>> {
        return flow {
            val result: MutableList<PackageInfo> = packageManager.getInstalledPackages(0).toMutableList()
            result.sortBy { it.packageName }
            if (manuallyAddAVirus) {
                result.first().packageName = virus1
            }
            if (fivePackagesOnly) {
                emit(result.take(5).toMutableList())
            } else {
                emit(result)
            }
        }.flowOn(Dispatchers.IO)
    }
}