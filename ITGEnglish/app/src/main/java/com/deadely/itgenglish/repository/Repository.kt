package com.deadely.itgenglish.repository

import com.deadely.itgenglish.database.AppDatabase
import com.deadely.itgenglish.database.mapToDBEntity
import com.deadely.itgenglish.database.mapToModelList
import com.deadely.itgenglish.model.User
import com.deadely.itgenglish.model.Word
import com.deadely.itgenglish.network.ITGService
import com.deadely.itgenglish.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(private val api: ITGService, private val db: AppDatabase) {

    suspend fun getAuth(
        email: String,
        password: String,
        year: String,
        gender: String
    ): Flow<DataState<String>> = flow {
        try {
            emit(DataState.Loading)
            val token = api.getAuth(User(email, password, year, gender))
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

    suspend fun getFavoritesList(): Flow<DataState<List<Word>>> = flow {
        try {
            emit(DataState.Loading)
            val favoritesList = db.getFavoriteDao().getAllFavos().mapToModelList()
            emit(DataState.Success(favoritesList))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun addToFavoriteList(word: Word) {
        db.getFavoriteDao().addFavos(word.mapToDBEntity())
    }

    fun deleteFromFavoriteList(word: Word) {
        db.getFavoriteDao().deleteFavos(word.mapToDBEntity())
    }

    suspend fun sendAudio(audioFile: File): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading)
            val reqFile: RequestBody = audioFile.asRequestBody("audio/*".toMediaTypeOrNull())
            val data = api.sendAudio(reqFile)
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

}
