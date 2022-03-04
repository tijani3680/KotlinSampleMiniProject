package com.tijani.rememberkotlinskills.core.repository

import com.tijani.rememberkotlinskills.core.dataSource.local.UserDao
import com.tijani.rememberkotlinskills.core.dataSource.remote.ApiHelper
import com.tijani.rememberkotlinskills.core.model.UserM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class UserRepository(private val apiHelper: ApiHelper, private val userDao: UserDao) {

    suspend fun getUserListFromServer(): Response<List<UserM>> {
        return apiHelper.getUserList();
    }

    suspend fun getUserListFromLocalDb(): List<UserM> {
        return userDao.getAllUsers()
    }

    suspend fun addUsersIntoLocalDb(users: List<UserM>) {
        userDao.addUser(users)
    }



    suspend fun getUserListFromServerFlow(): Flow<Response<List<UserM>>> {
        return flow {
            val usersList = apiHelper.getUserList()
            emit(usersList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

    suspend fun getUserListFromLocalDbFlow(): Flow<List<UserM>> {
        return flow {
            val usersList = userDao.getAllUsers()
            emit(usersList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }


}