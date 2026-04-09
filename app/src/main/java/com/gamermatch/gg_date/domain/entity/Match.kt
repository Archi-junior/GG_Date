package com.gamermatch.gg_date.domain.entity

import java.time.Instant

data class Match(
    val id: String,
    val userId1: String,
    val userId2: String,
    val status: MatchStatus,
    val matchedAd: Instant,
    val commonGames: List<String>,
    val messagesCount: Int
)

enum class MatchStatus {
    PENDING,
    MATCHED,
    IGNORED,
    BLOCKED,
    EXPIRED
}
