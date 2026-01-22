package io.github.bokchidevchan.core.data.repository

import io.github.bokchidevchan.core.common.IoDispatcher
import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.domain.repository.SearchRepository
import io.github.bokchidevchan.core.model.SearchResult
import io.github.bokchidevchan.core.network.ApiService
import io.github.bokchidevchan.core.network.dto.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SearchRepository {

    override suspend fun search(query: String, page: Int): Result<SearchResult> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.search(query, page)
                Result.Success(response.toDomain())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
