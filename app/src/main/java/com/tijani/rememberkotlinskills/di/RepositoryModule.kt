package com.tijani.rememberkotlinskills.di

import com.tijani.rememberkotlinskills.core.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get(), get()) }
}