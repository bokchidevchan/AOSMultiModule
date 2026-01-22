package io.github.bokchidevchan.feature.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _items = MutableStateFlow(
        listOf(
            HomeItem(id = 1, title = "Jetpack Compose", description = "Modern Android UI toolkit"),
            HomeItem(id = 2, title = "Hilt", description = "Dependency Injection for Android"),
            HomeItem(id = 3, title = "Navigation", description = "Type-safe navigation in Compose"),
            HomeItem(id = 4, title = "Multi-Module", description = "Scalable architecture pattern")
        )
    )
    val items: StateFlow<List<HomeItem>> = _items.asStateFlow()
}

data class HomeItem(
    val id: Int,
    val title: String,
    val description: String
)
