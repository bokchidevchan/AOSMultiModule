package io.github.bokchidevchan.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.bokchidevchan.core.model.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "author_id")
    val authorId: Long,

    @ColumnInfo(name = "author_name")
    val authorName: String,

    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String? = null,

    @ColumnInfo(name = "tags")
    val tags: List<String> = emptyList(),

    @ColumnInfo(name = "view_count")
    val viewCount: Int = 0,

    @ColumnInfo(name = "like_count")
    val likeCount: Int = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

fun ArticleEntity.toDomain(): Article = Article(
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

fun Article.toEntity(): ArticleEntity = ArticleEntity(
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

fun List<ArticleEntity>.toDomain(): List<Article> = map { it.toDomain() }
fun List<Article>.toEntity(): List<ArticleEntity> = map { it.toEntity() }
