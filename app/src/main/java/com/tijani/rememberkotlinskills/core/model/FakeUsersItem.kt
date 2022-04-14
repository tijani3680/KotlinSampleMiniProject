package com.tijani.rememberkotlinskills.core.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fakeUsers")
data class FakeUsersItem(
    @PrimaryKey
    @NonNull
    val id: Int,
    val title: String,
    val userId: Int,
    val body: String

)
