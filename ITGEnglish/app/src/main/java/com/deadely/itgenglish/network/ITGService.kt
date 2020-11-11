package com.deadely.itgenglish.network

import com.deadely.itgenglish.model.User
import com.deadely.itgenglish.utils.AUTH
import com.deadely.itgenglish.utils.LOGIN
import retrofit2.http.Body
import retrofit2.http.POST

interface ITGService {
    //    @GET(GET_AUDIO)
//    suspend fun getAudio(): List<Audio>
//
//    @POST(SEND_AUDIO)
//    suspend fun verifyAudio(): List<Audio>

    @POST(AUTH)
    suspend fun getAuth(@Body user: User): String

    @POST(LOGIN)
    suspend fun login(@Body user: User): String
}
