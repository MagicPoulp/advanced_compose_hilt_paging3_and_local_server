package com.example.testsecuritythierry.setup

import com.example.testsecuritythierry.repositories.PackageManagerRepository
import com.example.testsecuritythierry.repositories.VirusCheckerRepository
import com.example.testsecuritythierry.viewmodels.ApplicationsInspectorViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val testSecurityThierryInjectionModule = module {
    repositories()
    viewModels()
}

private fun Module.repositories() {
    factory { PackageManagerRepository() }
    factory { VirusCheckerRepository() }
}

fun Module.viewModels() {
    viewModel { ApplicationsInspectorViewModel(get(), get()) }
}
