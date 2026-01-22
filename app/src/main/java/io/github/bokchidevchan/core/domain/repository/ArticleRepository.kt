package io.github.bokchidevchan.core.domain.repository

import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(): Flow<Result<List<Article>>>
    fun getArticleById(id: Long): Flow<Result<Article>>
    fun getArticlesByAuthor(authorId: Long): Flow<Result<List<Article>>>
    suspend fun searchArticles(query: String): Result<List<Article>>
    suspend fun getPopularArticles(limit: Int): Result<List<Article>>
    suspend fun refreshArticles(): Result<Unit>
}
