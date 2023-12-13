package com.example.datastorewithhilt.data.repository

import com.example.datastorewithhilt.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userDataFlow: Flow<UserData>

    suspend fun setName(name: String)

    suspend fun setBirth(birth: String)
}