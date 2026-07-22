package com.gamermatch.gg_date.domain.entity

import java.time.Instant

data class Like(
    val id: String,
    val fromUserId: String,
    val toUserId: String,
    val type: LikeType,
    val createdAt: Instant,
)

enum class LikeType {
    LIKE, DISLIKE
}
