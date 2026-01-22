package io.github.bokchidevchan.core.data.repository

import io.github.bokchidevchan.core.common.IoDispatcher
import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.database.dao.ArticleDao
import io.github.bokchidevchan.core.database.entity.toDomain
import io.github.bokchidevchan.core.database.entity.toEntity
import io.github.bokchidevchan.core.domain.repository.ArticleRepository
import io.github.bokchidevchan.core.model.Article
import io.github.bokchidevchan.core.network.ApiService
import io.github.bokchidevchan.core.network.dto.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val articleDao: ArticleDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ArticleRepository {

    override fun getArticles(): Flow<Result<List<Article>>> {
        return articleDao.getAllArticles()
            .map<_, Result<List<Article>>> { entities ->
                Result.Success(entities.toDomain())
            }
            .catch { emit(Result.Error(it)) }
            .flowOn(ioDispatcher)
    }

    override fun getArticleById(id: Long): Flow<Result<Article>> {
        return articleDao.getArticleByIdFlow(id)
            .map<_, Result<Article>> { entity ->
                entity?.let { Result.Success(it.toDomain()) }
                    ?: Result.Error(NoSuchElementException("Article not found: $id"))
            }
            .catch { emit(Result.Error(it)) }
            .flowOn(ioDispatcher)
    }

    override fun getArticlesByAuthor(authorId: Long): Flow<Result<List<Article>>> {
        return articleDao.getArticlesByAuthor(authorId)
            .map<_, Result<List<Article>>> { entities ->
                Result.Success(entities.toDomain())
            }
            .catch { emit(Result.Error(it)) }
            .flowOn(ioDispatcher)
    }

    override suspend fun searchArticles(query: String): Result<List<Article>> {
        return withContext(ioDispatcher) {
            try {
                val articles = articleDao.searchArticles(query).toDomain()
                Result.Success(articles)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getPopularArticles(limit: Int): Result<List<Article>> {
        return withContext(ioDispatcher) {
            try {
                val articles = articleDao.getPopularArticles(limit).toDomain()
                Result.Success(articles)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun refreshArticles(): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val remoteArticles = apiService.getArticles()
                val entities = remoteArticles.toDomain().map { it.toEntity() }
                articleDao.insertArticles(entities)
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
