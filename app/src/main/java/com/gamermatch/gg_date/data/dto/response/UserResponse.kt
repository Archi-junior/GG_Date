package com.gamermatch.gg_date.data.dto.response

import java.time.Instant

data class UserResponse(
    val id: String,
    val username: String,
    val avatarUrl: String?,
    val age: Int,
    val bio: String?,
    val gamingProfile:  GamingProfileResponse,
    val lastActiveAt: Instant
)

data class MatchResponse(
    val matchId: String,
    val matchedUser: UserResponse,
    val compatibilityScore: Double,
    val matchedAt: Instant,
    val lastMessage: MessageResponse?
)

data class GamingProfileResponse(
    val id: String
)

data class MessageResponse(
    val id: String
)