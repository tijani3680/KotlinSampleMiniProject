package com.tijani.rememberkotlinskills.core.repository

import com.tijani.rememberkotlinskills.core.dataSource.local.FakeUserDao
import com.tijani.rememberkotlinskills.core.dataSource.remote.ApiHelper
import com.tijani.rememberkotlinskills.core.model.FakeUsersItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class FakeUserRepository(private val apiHelper: ApiHelper,private val fakeUserDao: FakeUserDao) {

    suspend fun getFakeUserFromServer():Flow<Response<List<FakeUsersItem>>>{
        return apiHelper.getFakeUsers()
    }

    suspend fun insertFakeUsersIntoLocalDb(fakeUsers:List<FakeUsersItem>){
        fakeUserDao.insertFakeUsers(fakeUsers)
    }

    fun getFakeUserFromLocalDb():Flow<List<FakeUsersItem>>{
        return fakeUserDao.getAllFakeUsers()
    }



}