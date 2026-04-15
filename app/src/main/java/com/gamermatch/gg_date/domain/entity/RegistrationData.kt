package com.gamermatch.gg_date.domain.entity

import java.time.LocalDate

data class RegistrationData(
    val email: String,
    val username: String,
    val password: String,
    val birthDate: LocalDate
)