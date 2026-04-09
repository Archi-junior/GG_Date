package com.gamermatch.gg_date.data.dto.request

import java.time.LocalDate

data class CreateProfileRequest(
    val email: String,
    val username: String,
    val password: String,
    val birthDate: LocalDate,
    val gamingProfile: GamingProfileDto
)

data class GamingProfileDto(
    val primaryPlatforms: List<String>,
    val favoriteGenre: List<String>,
    val favoriteGames: List<String>,
    val playStyle: String
)