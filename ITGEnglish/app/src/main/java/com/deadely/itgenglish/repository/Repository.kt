package com.deadely.itgenglish.repository

import com.deadely.itgenglish.model.User
import com.deadely.itgenglish.network.ITGService
import com.deadely.itgenglish.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val api: ITGService) {

    suspend fun getAuth(email: String, password: String): Flow<DataState<String>> = flow {
        try {
            emit(DataState.Loading)
            val token = api.getAuth(User(email, password))
            emit(DataState.Success(token))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun login(email: String, password: String): Flow<DataState<String>> = flow {
        try {
            emit(DataState.Loading)
            val token = api.login(User(email, password))
            emit(DataState.Success(token))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}
