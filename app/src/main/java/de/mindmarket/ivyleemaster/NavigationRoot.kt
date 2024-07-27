package de.mindmarket.ivyleemaster

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.mindmarket.ivyleemaster.auth.login.presentation.LoginScreenRoot
import de.mindmarket.ivyleemaster.auth.register.presentation.RegisterScreenRoot
import de.mindmarket.ivyleemaster.task.presentation.TaskScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "main" else "auth"
    ) {
        authGraph(navController)
        mainGraph(navController)
    }

}

private fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        route = "auth",
        startDestination = "login"
    ) {
        composable(route = "login") {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate("ivy_task") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }

        composable(route="register") {
            RegisterScreenRoot(
                onNavigateToLoginClick = {
                    navController.navigate("login") {
                        popUpTo("register") {
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
        startDestination = "ivy_task"
    ) {
        composable(route = "ivy_task") {
            TaskScreenRoot()

        }
        composable(route = "ivy_idea") {

        }
        composable(route = "ivy_create") {

        }
    }
}