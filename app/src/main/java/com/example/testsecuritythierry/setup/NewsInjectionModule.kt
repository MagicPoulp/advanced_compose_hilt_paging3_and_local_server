package com.example.testsecuritythierry.setup

import com.example.testsecuritythierry.repositories.NewsDataRepository
import com.example.testsecuritythierry.viewmodels.NewsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val newsInjectionModule = module {
    repositories()
    viewModels()
}

private fun Module.repositories() {
    factory { NewsDataRepository() }
}

fun Module.viewModels() {
    viewModel { NewsViewModel(get()) }
}
