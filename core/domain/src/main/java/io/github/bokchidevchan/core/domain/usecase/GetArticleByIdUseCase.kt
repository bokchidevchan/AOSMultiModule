package io.github.bokchidevchan.core.domain.usecase

import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.domain.repository.ArticleRepository
import io.github.bokchidevchan.core.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    operator fun invoke(id: Long): Flow<Result<Article>> {
        return articleRepository.getArticleById(id)
    }
}
