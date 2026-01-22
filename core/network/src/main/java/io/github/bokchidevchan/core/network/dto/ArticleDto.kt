package io.github.bokchidevchan.core.network.dto

import io.github.bokchidevchan.core.model.Article
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author_id") val authorId: Long,
    @SerialName("author_name") val authorName: String,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("view_count") val viewCount: Int = 0,
    @SerialName("like_count") val likeCount: Int = 0,
    @SerialName("created_at") val createdAt: Long = 0,
    @SerialName("updated_at") val updatedAt: Long = 0
)

fun ArticleDto.toDomain(): Article = Article(
    id = id,
    title = title,
    content = content,
    authorId = authorId,
    authorName = authorName,
    thumbnailUrl = thumbnailUrl,
    tags = tags,
    viewCount = viewCount,
    likeCount = likeCount,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun List<ArticleDto>.toDomain(): List<Article> = map { it.toDomain() }
