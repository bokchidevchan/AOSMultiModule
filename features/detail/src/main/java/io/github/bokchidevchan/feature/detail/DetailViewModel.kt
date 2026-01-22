package io.github.bokchidevchan.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.domain.usecase.GetArticleByIdUseCase
import io.github.bokchidevchan.core.model.Article
import io.github.bokchidevchan.core.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getArticleByIdUseCase: GetArticleByIdUseCase
) : ViewModel() {

    private val route: Route.Detail = savedStateHandle.toRoute<Route.Detail>()

    private val _uiState = MutableStateFlow(
        DetailUiState(
            itemId = route.itemId,
            title = route.title
        )
    )
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadArticle()
    }

    fun loadArticle() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getArticleByIdUseCase(route.itemId.toLong()).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                article = result.data,
                                error = null
                            )
                        }
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.exception.message ?: "Unknown error"
                            )
                        }
                    }
                }
            }
        }
    }

    fun retry() {
        loadArticle()
    }
}

data class DetailUiState(
    val itemId: Int,
    val title: String,
    val isLoading: Boolean = false,
    val article: Article? = null,
    val error: String? = null
)
