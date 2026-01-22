package io.github.bokchidevchan.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val name: String,
    val email: String,
    val avatarUrl: String? = null,
    val bio: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
