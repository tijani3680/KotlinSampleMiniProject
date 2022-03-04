package com.tijani.rememberkotlinskills.core.dataSource.local

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.tijani.rememberkotlinskills.core.model.UserM

@Database(entities = arrayOf(UserM::class), version = 1, exportSchema = false)
abstract class DatabaseHelper : RoomDatabase() {
    abstract val userDao: UserDao
}