package com.example.testsecuritythierry.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.example.testsecuritythierry.data.models.DataNewsElement
import com.example.testsecuritythierry.ui.setup.RoutingScreen
import com.example.testsecuritythierry.ui.view_models.NewsViewModel

@Composable
fun NavigationScreen(
    stateListNews: LazyPagingItems<DataNewsElement>,
    newsViewModel: NewsViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val items = listOf(
        RoutingScreen.MyListScreen,
        RoutingScreen.MyDetailScreen,
    )
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = when(screen) {
                                    RoutingScreen.MyListScreen -> Icons.Filled.Favorite
                                    RoutingScreen.MyDetailScreen -> Icons.Filled.AccountBox
                                },
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = RoutingScreen.MyListScreen.route,
            androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable(RoutingScreen.MyListScreen.route) {
                ListScreen(
                    stateListNews = stateListNews,
                    navController = navController
                )
            }
            composable(RoutingScreen.MyDetailScreen.route) { backStackEntry ->
                val previousRow = newsViewModel.activeRow
                val rowId = try {
                    backStackEntry.arguments?.getString("rowId")?.toInt() ?: previousRow
                }
                catch (_: Exception)
                {
                    previousRow
                }
                newsViewModel.activeRow = rowId
                DetailScreen(stateListNews = stateListNews, rowId = rowId)
            }
        }
    }
}
