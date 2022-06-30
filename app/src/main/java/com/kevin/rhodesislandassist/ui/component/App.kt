package com.kevin.rhodesislandassist.ui.component

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.ui.theme.getColorScheme
import com.kevin.rhodesislandassist.ui.viewmodel.DataViewModel

sealed class Page(val route: String, @StringRes val pageName: Int, val icon: ImageVector) {
    object WelcomePage : Page("welcome", R.string.page_welcome, Icons.Filled.Home)
    object SearchPage : Page("search", R.string.page_search, Icons.Filled.Search)
    object MorePage : Page("more", R.string.page_more, Icons.Filled.More)
}

private fun getSelectedItem(route: String?): Page {
    return when (route) {
        Page.WelcomePage.route -> Page.WelcomePage
        Page.SearchPage.route -> Page.SearchPage
        Page.MorePage.route -> Page.MorePage
        else -> Page.WelcomePage
    }
}

@SuppressLint("")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(viewModel: DataViewModel) {
    val navItems = listOf(Page.WelcomePage, Page.SearchPage, Page.MorePage)
    val navController = rememberNavController()
    LocalContext.current
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestinationRoute = currentBackStackEntry?.destination?.route
    Surface {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(
                                id = getSelectedItem(
                                    currentDestinationRoute
                                ).pageName
                            )
                        )
                    }
                )
            },
            bottomBar = {
                NavigationBar(containerColor = getColorScheme(isSystemInDarkTheme()).primaryContainer) {
                    navItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.route
                                )
                            },
                            label = { Text(text = stringResource(id = item.pageName)) },
                            selected = currentBackStackEntry?.destination?.route == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = getColorScheme(isSystemInDarkTheme()).primary,
                                selectedIconColor = getColorScheme(isSystemInDarkTheme()).background
                            )
                        )
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = navItems[0].route,
                modifier = Modifier
                    .padding(it)
                    .background(color = getColorScheme(isSystemInDarkTheme()).background)
            ) {
                composable(Page.WelcomePage.route) { Welcome() }
                composable(Page.SearchPage.route) { Search(viewModel) }
                composable(Page.MorePage.route) { More(viewModel) }
            }
        }
    }
}