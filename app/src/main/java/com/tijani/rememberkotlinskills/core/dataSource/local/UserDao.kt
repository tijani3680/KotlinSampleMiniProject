package com.tijani.rememberkotlinskills.core.dataSource.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tijani.rememberkotlinskills.core.model.UserM

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(users: List<UserM>)

    @Query("SELECT * FROM users ORDER BY id DESC")
    suspend fun getAllUsers(): List<UserM>

}