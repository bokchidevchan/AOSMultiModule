package io.github.bokchidevchan.core.network.dto

import io.github.bokchidevchan.core.model.SearchResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("query") val query: String,
    @SerialName("articles") val articles: List<ArticleDto>,
    @SerialName("users") val users: List<UserDto>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("page") val page: Int,
    @SerialName("has_more") val hasMore: Boolean
)

fun SearchResponseDto.toDomain(): SearchResult = SearchResult(
    query = query,
    articles = articles.toDomain(),
    users = users.toDomain(),
    totalCount = totalCount,
    page = page,
    hasMore = hasMore
)
