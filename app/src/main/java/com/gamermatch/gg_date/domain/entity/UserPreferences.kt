package com.gamermatch.gg_date.domain.entity

data class UserPreferences(
    val ageRange: ClosedRange<Int>,
    val preferredGenders: List<Gender>,
    val preferredPlatforms: List<GamingPlatform>,
    val preferredGenres: List<Genre>,
    val preferredPlayStyles: List<PlayStyle>,
    val preferredGames: List<String>,
    val lookingFor: List<LookingFor>,
    val showMeInSearch: Boolean = true,
    val receiveNotifications: Boolean = true
){
    companion object{
        val EMPTY = UserPreferences(
            ageRange = 18..99,
            preferredGenders = emptyList(),
            preferredPlatforms = emptyList(),
            preferredGenres = emptyList(),
            preferredPlayStyles = emptyList(),
            preferredGames = emptyList(),
            lookingFor = emptyList(),
            showMeInSearch = true,
            receiveNotifications = true
        )
    }
}

enum class Gender {
    MALE, FEMALE
}
