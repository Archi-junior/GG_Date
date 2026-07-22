package com.gamermatch.gg_date.ui.registration

import java.time.LocalDate

data class UiState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val birthDate: LocalDate? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegistrationSuccess: Boolean = false,
    val emailError: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val birthDateError: String? = null
)
