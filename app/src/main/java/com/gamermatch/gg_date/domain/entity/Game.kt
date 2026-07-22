package com.gamermatch.gg_date.domain.entity

data class Game(
    val id: String,
    val name: String,
    val coverUrl: String?,
    val genres: List<Genre>,
    val platforms: List<GamingPlatform>,
    val isCrossPlatform: Boolean
)
