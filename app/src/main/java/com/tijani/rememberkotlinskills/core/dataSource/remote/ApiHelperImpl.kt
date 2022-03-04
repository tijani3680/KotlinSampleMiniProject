package com.tijani.rememberkotlinskills.core.dataSource.remote

import com.tijani.rememberkotlinskills.core.model.UserM
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUserList(): Response<List<UserM>> {
        return apiService.getUserList()
    }

    override suspend fun getUser(id: String): Response<UserM> {
        return apiService.getUser(id)
    }

}