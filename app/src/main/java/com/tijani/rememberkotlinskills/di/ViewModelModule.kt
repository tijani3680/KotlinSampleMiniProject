package com.tijani.rememberkotlinskills.di

import com.tijani.rememberkotlinskills.ui.viewmodel.UserVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserVM(get(), get())
    }
}

