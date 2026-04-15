package com.gamermatch.gg_date.domain.usecase

import com.gamermatch.gg_date.domain.entity.RegistrationResult
import com.gamermatch.gg_date.domain.entity.User
import com.gamermatch.gg_date.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke (user: User): Flow<RegistrationResult> {
        return when {
            !isValidEmail(user.email) -> flowOf(RegistrationResult.Error("Некорректный email"))
            !isValidPassword(user.password) -> flowOf(RegistrationResult.Error("Пароль должен содержать минимум 6 символов"))
            !isValidUsername(user.username) -> flowOf(RegistrationResult.Error("Имя пользователя должно содержать 3-20 символов"))
            else -> repository.register(user)
        }
    }

    private fun isValidEmail(email: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean{
        return password.length>=6
    }

    private fun isValidUsername(username: String): Boolean{
        return username.length in 3..20 && username.matches(Regex("^[a-zA-Z0-9_]+$"))
    }
}