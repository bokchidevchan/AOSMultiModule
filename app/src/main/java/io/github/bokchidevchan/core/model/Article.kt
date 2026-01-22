package io.github.bokchidevchan.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Long,
    val title: String,
    val content: String,
    val authorId: Long,
    val authorName: String,
    val thumbnailUrl: String? = null,
    val tags: List<String> = emptyList(),
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
