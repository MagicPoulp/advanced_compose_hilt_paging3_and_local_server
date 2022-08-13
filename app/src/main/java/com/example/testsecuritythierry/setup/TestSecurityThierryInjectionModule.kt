package com.example.testsecuritythierry.setup

import com.example.testsecuritythierry.repositories.PackageManagerRepository
import com.example.testsecuritythierry.viewmodels.ApplicationsInspectorViewModel
import org.koin.dsl.module

val testSecurityThierryInjectionModule = module {

    single { PackageManagerRepository() }

    single { ApplicationsInspectorViewModel(get()) }
}
