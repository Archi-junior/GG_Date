package com.gamermatch.gg_date.data.network

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun register(@Body reguest: RegisterRequest): RegisterResponse
}