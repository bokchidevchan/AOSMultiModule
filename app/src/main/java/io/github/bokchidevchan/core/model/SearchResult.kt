package io.github.bokchidevchan.core.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val query: String,
    val articles: List<Article>,
    val users: List<User>,
    val totalCount: Int,
    val page: Int,
    val hasMore: Boolean
)
