package io.github.bokchidevchan.core.domain.usecase

import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.domain.repository.ArticleRepository
import io.github.bokchidevchan.core.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    operator fun invoke(): Flow<Result<List<Article>>> {
        return articleRepository.getArticles()
    }
}
