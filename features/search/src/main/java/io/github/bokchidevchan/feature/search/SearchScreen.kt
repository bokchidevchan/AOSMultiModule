package io.github.bokchidevchan.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.bokchidevchan.core.ui.component.ArticleCard
import io.github.bokchidevchan.core.ui.component.EmptyView
import io.github.bokchidevchan.core.ui.component.ErrorView
import io.github.bokchidevchan.core.ui.component.LoadingIndicator
import io.github.bokchidevchan.core.ui.component.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onArticleClick: (Long) -> Unit,
    onUserClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("검색") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchBar(
                query = uiState.query,
                onQueryChange = viewModel::onQueryChange,
                onSearch = viewModel::search,
                modifier = Modifier.padding(16.dp)
            )

            when {
                uiState.isLoading -> {
                    LoadingIndicator()
                }
                uiState.error != null -> {
                    ErrorView(
                        message = uiState.error!!,
                        onRetry = viewModel::search
                    )
                }
                uiState.hasSearched && uiState.articles.isEmpty() && uiState.users.isEmpty() -> {
                    EmptyView(
                        message = "검색 결과가 없습니다",
                        icon = Icons.Default.Search
                    )
                }
                uiState.hasSearched -> {
                    SearchResults(
                        articles = uiState.articles,
                        totalCount = uiState.totalCount,
                        onArticleClick = onArticleClick
                    )
                }
                else -> {
                    EmptyView(
                        message = "검색어를 입력해주세요",
                        icon = Icons.Default.Search
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchResults(
    articles: List<io.github.bokchidevchan.core.model.Article>,
    totalCount: Int,
    onArticleClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "검색 결과 ${totalCount}건",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(articles) { article ->
            ArticleCard(
                article = article,
                onClick = { onArticleClick(article.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
