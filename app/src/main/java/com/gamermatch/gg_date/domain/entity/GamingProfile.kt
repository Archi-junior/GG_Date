package com.gamermatch.gg_date.domain.entity

data class GamingProfile(
    val primaryPlatforms: List<GamingPlatform>,
    val favoriteGenres: List<Genre>,
    val favoriteGames: List<Game>,
    val playStyle: PlayStyle?,
    val skillLevel: Map<String,SkillLevel>?,
    val lookingFor: List<LookingFor>?,
    val voiceChatPreference: VoiceChatPreference?
){
    companion object {
        val EMPTY = GamingProfile(
            primaryPlatforms = emptyList(),
            favoriteGenres = emptyList(),
            favoriteGames = emptyList(),
            playStyle = null,
            skillLevel = null,
            lookingFor = null,
            voiceChatPreference = null,
        )
    }
}

enum class GamingPlatform{
    PC, XBOX, PLAYSTATION, NINTENDO_SWITCH, MOBILE
}

enum class Genre{
    MOBA, FPS, RPG, BATTLE_ROYALE, SURVIVAL, STRATEGY, SIMULATION, CASUAL, INDIE, MMO
}

enum class PlayStyle{
    COMPETITIVE, CASUAL, HARDCORE, STORY_DRIVEN, SOCIAL, EXPLORER
}

enum class SkillLevel {
    BEGINNER, INTERMEDIATE, ADVANCED, EXPERT, PROFESSIONAL
}

enum class LookingFor{
    DUO_PARTNER, TEAM_MATE, COOP_BUDDY, FRIENDSHIP, ROMANCE, COACHING, TOURNAMENT_TEAM
}

enum class VoiceChatPreference {
    ALWAYS, SOMETIMES, NEVER, DEPENDS_ON_TEAM
}