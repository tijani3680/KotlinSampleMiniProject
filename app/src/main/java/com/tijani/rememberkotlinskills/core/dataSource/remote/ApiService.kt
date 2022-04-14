package com.tijani.rememberkotlinskills.core.dataSource.remote

import com.tijani.rememberkotlinskills.core.model.FakeUsersItem
import com.tijani.rememberkotlinskills.core.model.UserM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("GetListUsersTest.php")
    suspend fun getUserList(): Response<List<UserM>>

    @POST("GetListUsersTest.php")
    suspend fun getUser(@Query("id") id: String): Response<UserM>

    @GET("posts")
    suspend fun getFakeUsers():Response<List<FakeUsersItem>>
}