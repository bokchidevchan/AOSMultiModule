package io.github.bokchidevchan.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.bokchidevchan.core.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String? = null,

    @ColumnInfo(name = "bio")
    val bio: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name,
    email = email,
    avatarUrl = avatarUrl,
    bio = bio,
    createdAt = createdAt
)

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
    email = email,
    avatarUrl = avatarUrl,
    bio = bio,
    createdAt = createdAt
)

fun List<UserEntity>.toDomain(): List<User> = map { it.toDomain() }
fun List<User>.toEntity(): List<UserEntity> = map { it.toEntity() }
