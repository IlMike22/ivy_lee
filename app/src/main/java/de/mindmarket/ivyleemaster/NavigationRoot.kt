package de.mindmarket.ivyleemaster

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.mindmarket.ivyleemaster.auth.login.presentation.LoginScreenRoot
import de.mindmarket.ivyleemaster.auth.register.presentation.RegisterScreenRoot
import de.mindmarket.ivyleemaster.idea.presentation.IdeaScreenRoot
import de.mindmarket.ivyleemaster.settings.SettingsScreenRoot
import de.mindmarket.ivyleemaster.task.presentation.TaskScreenRoot
import de.mindmarket.ivyleemaster.util.presentation.Route

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Route.MAIN else Route.AUTH
    ) {
        authGraph(navController)
        mainGraph(navController)
    }

}

private fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        route = Route.AUTH,
        startDestination = Route.LOGIN
    ) {
        composable(route = Route.LOGIN) {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(Route.TASK) {
                        popUpTo(Route.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Route.REGISTER) {
                        popUpTo(Route.LOGIN) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }

        composable(route = Route.REGISTER) {
            RegisterScreenRoot(
                onNavigateToLoginClick = {
                    navController.navigate(Route.LOGIN) {
                        popUpTo(Route.REGISTER) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        route = "main",
        startDestination = Route.TASK
    ) {
        composable(route = Route.TASK) {
            TaskScreenRoot()

        }
        composable(route = Route.IDEA) {
            IdeaScreenRoot()

        }
        composable(route = Route.SETTINGS) {
            SettingsScreenRoot()
        }
    }
}

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Task : Screen(route = Route.TASK, label = "Home", icon = Icons.Default.Home)
    data object Idea : Screen(route = Route.IDEA, label = "Idea Pool", icon = Icons.Default.ShoppingCart)
    data object Settings : Screen(route = Route.SETTINGS, label = "Settings", icon = Icons.Default.Settings)
}