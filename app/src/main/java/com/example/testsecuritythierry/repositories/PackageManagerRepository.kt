package com.example.testsecuritythierry.repositories

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class PackageManagerRepository: KoinComponent {

    suspend fun getPackages(packageManager: PackageManager): Flow<MutableList<PackageInfo>> {
        return flow {
            emit(packageManager.getInstalledPackages(0))
        }.flowOn(Dispatchers.IO)
    }
}