package com.gamermatch.gg_date.domain.entity

import android.location.Location
import java.time.Instant
import java.time.LocalDate

data class User(
    val id: String,
    val email: String,
    val password: String,
    val username: String,
    val avatarUrl: String?,
    val bio: String,
    val birthDate: LocalDate,
    val location: Location,
    val gamingProfile: GamingProfile,
    val preferences: UserPreferences,
    val accountStatus: AccountStatus,
    val createdAt: Instant,
    val lastActiveAt: Instant,
    val isVerified: Boolean
)

enum class AccountStatus {
    ACTIVE, SUSPENDED, DELETED, BANNED
}

sealed class RegistrationResult{
    object Succes : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
    object Loading : RegistrationResult()
}