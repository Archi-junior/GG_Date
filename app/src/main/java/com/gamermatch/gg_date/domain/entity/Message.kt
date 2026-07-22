package com.gamermatch.gg_date.domain.entity

import java.time.Instant

data class Message(
    val id: String,
    val matchId: String,
    val fromUserId: String,
    val content: String,
    val sentAt: Instant,
    val isRead: Boolean,
    val readAt: Instant?,
    val type: MessageType
)

enum class MessageType {
    TEXT, IMAGE, GAME_INVITE, VOICE_MESSAGE
}
