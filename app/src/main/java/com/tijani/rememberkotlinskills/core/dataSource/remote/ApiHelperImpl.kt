package com.tijani.rememberkotlinskills.core.dataSource.remote

import com.tijani.rememberkotlinskills.core.model.UserM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUserList(): Response<List<UserM>> {
        return apiService.getUserList()
    }

    override suspend fun getUser(id: String): Response<UserM> {
        return apiService.getUser(id)
    }

    override suspend fun getUsers(): Flow<Response<List<UserM>>> {
        return flow {emit( apiService.getUserList() )}.flowOn(Dispatchers.IO)
    }

}