package com.tijani.rememberkotlinskills.di

import com.tijani.rememberkotlinskills.core.dataSource.dataStore.DataStoreHelper
import org.koin.dsl.module

val appModule = module {
    single { DataStoreHelper(get()) }
}