package com.tijani.rememberkotlinskills.core.dataSource.remote

import com.tijani.rememberkotlinskills.core.model.UserM
import retrofit2.Response

interface ApiHelper {

    suspend fun getUserList(): Response<List<UserM>>

    suspend fun getUser(id: String): Response<UserM>


}