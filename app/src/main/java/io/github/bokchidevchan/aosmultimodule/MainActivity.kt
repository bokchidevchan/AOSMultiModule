package io.github.bokchidevchan.aosmultimodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.bokchidevchan.aosmultimodule.ui.theme.AOSMultiModuleTheme
import io.github.bokchidevchan.core.navigation.Route
import io.github.bokchidevchan.feature.detail.detailScreen
import io.github.bokchidevchan.feature.home.homeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AOSMultiModuleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavHost()
                }
            }
        }
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier
    ) {
        homeScreen(
            onNavigateToDetail = { itemId, title ->
                navController.navigate(Route.Detail(itemId = itemId, title = title))
            }
        )

        detailScreen(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
