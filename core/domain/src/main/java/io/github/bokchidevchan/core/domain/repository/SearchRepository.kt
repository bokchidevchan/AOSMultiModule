package io.github.bokchidevchan.core.domain.repository

import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.model.SearchResult

interface SearchRepository {
    suspend fun search(query: String, page: Int = 1): Result<SearchResult>
}
