package io.github.bokchidevchan.core.domain.usecase

import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.domain.repository.SearchRepository
import io.github.bokchidevchan.core.model.SearchResult
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1): Result<SearchResult> {
        if (query.isBlank()) {
            return Result.Success(
                SearchResult(
                    query = "",
                    articles = emptyList(),
                    users = emptyList(),
                    totalCount = 0,
                    page = 1,
                    hasMore = false
                )
            )
        }
        return searchRepository.search(query.trim(), page)
    }
}
