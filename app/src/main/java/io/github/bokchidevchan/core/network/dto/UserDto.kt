package io.github.bokchidevchan.core.network.dto

import io.github.bokchidevchan.core.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("bio") val bio: String? = null,
    @SerialName("created_at") val createdAt: Long = 0
)

fun UserDto.toDomain(): User = User(
    id = id,
    name = name,
    email = email,
    avatarUrl = avatarUrl,
    bio = bio,
    createdAt = createdAt
)

fun List<UserDto>.toDomain(): List<User> = map { it.toDomain() }
