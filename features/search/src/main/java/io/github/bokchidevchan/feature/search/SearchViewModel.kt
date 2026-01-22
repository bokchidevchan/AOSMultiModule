package io.github.bokchidevchan.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.domain.usecase.SearchUseCase
import io.github.bokchidevchan.core.model.Article
import io.github.bokchidevchan.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun search() {
        val query = _uiState.value.query
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = searchUseCase(query)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            articles = result.data.articles,
                            users = result.data.users,
                            totalCount = result.data.totalCount,
                            hasSearched = true
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exception.message ?: "검색 중 오류가 발생했습니다"
                        )
                    }
                }
                is Result.Loading -> {
                    // Already handled above
                }
            }
        }
    }

    fun clearSearch() {
        _uiState.update {
            SearchUiState()
        }
    }
}

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val users: List<User> = emptyList(),
    val totalCount: Int = 0,
    val hasSearched: Boolean = false,
    val error: String? = null
)
