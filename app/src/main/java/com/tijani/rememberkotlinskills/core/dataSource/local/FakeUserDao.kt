package com.tijani.rememberkotlinskills.core.dataSource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tijani.rememberkotlinskills.core.model.FakeUsersItem
import com.tijani.rememberkotlinskills.core.model.UserM
import kotlinx.coroutines.flow.Flow

@Dao
interface FakeUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFakeUsers(users:List<FakeUsersItem>)


    @Query("SELECT * FROM fakeUsers ORDER BY id DESC")
     fun getAllFakeUsers(): Flow<List<FakeUsersItem>>



}