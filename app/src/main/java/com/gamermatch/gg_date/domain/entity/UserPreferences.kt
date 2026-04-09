package com.gamermatch.gg_date.domain.entity

data class UserPreferences(
    val ageRange: ClosedRange<Int>,
    val preferredGender: List<Gender>,
    val preferredPlatforms: List<GamingPlatform>,
    val preferredGenres: List<Genre>,
    val preferredPlayStyles: List<PlayStyle>,
    val preferredGames: List<String>,
    val lookingFor: List<LookingFor>,
    val showMeInSearch: Boolean = true,
    val receiveNotifications: Boolean = true
)

enum class Gender {
    MALE, FEMALE
}
