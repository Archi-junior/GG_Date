package com.gamermatch.gg_date.ui.registration

import android.os.Build
import android.util.Patterns.EMAIL_ADDRESS
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamermatch.gg_date.domain.entity.AccountStatus
import com.gamermatch.gg_date.domain.entity.GamingProfile
import com.gamermatch.gg_date.domain.entity.RegistrationData
import com.gamermatch.gg_date.domain.entity.RegistrationResult
import com.gamermatch.gg_date.domain.entity.User
import com.gamermatch.gg_date.domain.entity.UserPreferences
import com.gamermatch.gg_date.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val registerUserUseCase: RegisterUserUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = validateEmail(email)
        )
    }

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username,
            usernameError = validateUsername(username)
        )
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = validatePassword(password)
        )
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
        checkPasswordsMatch()
    }

    private fun checkPasswordsMatch() {
        val currentState = _uiState.value
        if (currentState.confirmPassword.isNotEmpty() &&
            currentState.password != currentState.confirmPassword) {
            _uiState.value = currentState.copy(
                confirmPasswordError = "Пароли не совпадают"
            )
        } else {
            _uiState.value = currentState.copy(
                confirmPasswordError = null
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateBirthDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(
            birthDate = date,
            birthDateError = validateBirthDate(date)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateBirthDate(date: LocalDate?): String? {
        if (date == null) return "Дата рождения обязательна"
        val age = java.time.Period.between(date, LocalDate.now()).years
        if (age < 18) return "Вам должно быть минимум 18 лет"
        if (age > 100) return "Проверьте дату рождения"
        return null
    }

    fun validateEmail(email:String): String? {
        if(email.isBlank()) return "Поле email не должно быть пустым"
        return if(!EMAIL_ADDRESS.matcher(email).matches()) "некорректное поле email"
        else null
    }

    fun validateUsername(username:String): String? {
        if(username.isBlank()) return "Поле username не должно быть пустым"
        if(username.length < 3) return  "Username должен содержать минимум 3 символа"
        if(username.length > 20) return "Username максимум 20 символов"
        if (!username.matches(Regex("^[a-zA-Z0-9_]+$"))) {
            return "Только буквы, цифры и _"
        }
        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) return "Пароль обязателен"
        if (password.length < 6) return "Минимум 6 символов"
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun register (registrationData: RegistrationData) {
        val currentState = _uiState.value
        val emailError = validateEmail(currentState.email)
        val usernameError = validateUsername(currentState.username)
        val passwordError = validatePassword(currentState.password)
        val birthDateError = validateBirthDate(currentState.birthDate)
        val confirmPasswordError = if (currentState.password != currentState.confirmPassword) {
            "Пароли не совпадают"
        } else null
        if(emailError != null || usernameError != null || passwordError != null ||
            birthDateError != null || confirmPasswordError != null) {
            _uiState.value = currentState.copy(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
                birthDateError = birthDateError,
                confirmPasswordError = confirmPasswordError
            )
            return
        }

        val user = User(
            id = UUID.randomUUID().toString(),
            email = registrationData.email,
            password = registrationData.password,
            username = registrationData.username,
            avatarUrl = null,
            bio = "",
            birthDate = registrationData.birthDate,
            location = null,
            gamingProfile = GamingProfile.EMPTY,
            preferences = UserPreferences.EMPTY,
            accountStatus = AccountStatus.ACTIVE,
            createdAt = Instant.now(),
            lastActiveAt = Instant.now(),
            isVerified = false
        )

        viewModelScope.launch {
            registerUserUseCase(user).collect { result ->
                when(result) {
                    RegistrationResult.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is RegistrationResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isRegistrationSuccess = true
                        )
                    }
                    is RegistrationResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }


    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetSuccess() {
        _uiState.value = _uiState.value.copy(isRegistrationSuccess = false)
    }

}