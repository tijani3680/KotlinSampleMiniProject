package com.tijani.rememberkotlinskills.di

import android.app.Application
import androidx.room.Room
import com.tijani.rememberkotlinskills.core.dataSource.local.DatabaseHelper
import com.tijani.rememberkotlinskills.core.dataSource.local.FakeUserDao
import com.tijani.rememberkotlinskills.core.dataSource.local.UserDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { provideDataBase(androidApplication()) }
    single { provideUserDao(get()) }
    single { provideFakeUserDao(get()) }
}


fun provideDataBase(application: Application): DatabaseHelper {
    return Room.databaseBuilder(application, DatabaseHelper::class.java, "TijaniDatabase")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideUserDao(dataBase: DatabaseHelper): UserDao {
    return dataBase.userDao
}

fun provideFakeUserDao(dataBase: DatabaseHelper): FakeUserDao {
    return dataBase.fakeUserDao
}
