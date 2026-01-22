package io.github.bokchidevchan.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bokchidevchan.core.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route: Route.Detail = savedStateHandle.toRoute<Route.Detail>()

    private val _uiState = MutableStateFlow(
        DetailUiState(
            itemId = route.itemId,
            title = route.title,
            description = "This is the detailed information for item #${route.itemId}."
        )
    )
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
}

data class DetailUiState(
    val itemId: Int,
    val title: String,
    val description: String
)
