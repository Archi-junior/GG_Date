package com.gamermatch.gg_date.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.gamermatch.gg_date.data.network.ApiService
import com.gamermatch.gg_date.domain.entity.RegistrationResult
import com.gamermatch.gg_date.domain.entity.User
import com.gamermatch.gg_date.domain.repository.AuthRepository
import com.gamermatch.gg_date.data.network.RegisterRequest
import com.gamermatch.gg_date.data.network.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class AuthRepositoryImpl(
    private val api: ApiService
) : AuthRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun register(user: User): Flow<RegistrationResult> = flow {
        emit(RegistrationResult.Loading)
        try {
            val request = RegisterRequest(
                email = user.email,
                username = user.username,
                password = user.password,
                birthDate = user.birthDate.toString()
            )
            val response = api.register(request)
            if (response.isSuccess) {
                saveUserData(response)
                emit(RegistrationResult.Success)
            } else {
                emit(RegistrationResult.Error(response.message ?: "Ошибка регистрации"))
            }
        } catch (e: HttpException) {
            emit(RegistrationResult.Error("Сетевая ошибка: ${e.message}"))
        } catch (_: IOException) {
            emit(RegistrationResult.Error("Нет подключения к интернету"))
        }
    }

    private fun saveUserData(response: RegisterResponse) {

    }
}