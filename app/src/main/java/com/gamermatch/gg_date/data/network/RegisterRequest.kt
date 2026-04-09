package com.gamermatch.gg_date.data.network

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    val email: String,
    val username: String,
    val password: String,
    val birthDate: String
)

@Serializable
data class RegisterResponse(
    val isSuccess: Boolean,
    val message: String?,
    val token: String?,
    val userId: String?
)