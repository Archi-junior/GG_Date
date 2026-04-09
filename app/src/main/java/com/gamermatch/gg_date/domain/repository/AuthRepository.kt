package com.gamermatch.gg_date.domain.repository

import com.gamermatch.gg_date.domain.entity.RegistrationResult
import com.gamermatch.gg_date.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun register(user: User): Flow<RegistrationResult>
}