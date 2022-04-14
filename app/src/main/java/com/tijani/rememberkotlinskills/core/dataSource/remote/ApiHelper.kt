package com.tijani.rememberkotlinskills.core.dataSource.remote

import com.tijani.rememberkotlinskills.core.model.FakeUsersItem
import com.tijani.rememberkotlinskills.core.model.UserM
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ApiHelper {

    suspend fun getUserList(): Response<List<UserM>>

    suspend fun getUser(id: String): Response<UserM>

    suspend fun getUsers():Flow<Response<List<UserM>>>

    suspend fun getFakeUsers():Flow<Response<List<FakeUsersItem>>>


}