package de.mindmarket.ivyleemaster

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import de.mindmarket.ivyleemaster.add_idea.presentation.AddIdeaScreenRoot
import de.mindmarket.ivyleemaster.auth.login.presentation.LoginScreenRoot
import de.mindmarket.ivyleemaster.auth.register.presentation.RegisterScreenRoot
import de.mindmarket.ivyleemaster.core.presentation.components.IvyBottomAppBar
import de.mindmarket.ivyleemaster.idea.presentation.IdeaScreenRoot
import de.mindmarket.ivyleemaster.settings.SettingsScreenRoot
import de.mindmarket.ivyleemaster.task.presentation.TaskAction
import de.mindmarket.ivyleemaster.task.presentation.TaskScreenRoot
import de.mindmarket.ivyleemaster.task.presentation.TaskViewModel
import de.mindmarket.ivyleemaster.util.presentation.Destination
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    isRefreshUI: Boolean,
    onRefreshUI: (Boolean) -> Unit
) {
    Scaffold(
        bottomBar = {
            IvyBottomAppBar(
                isVisible = isUserLoggedIn,
                screens = listOf(
                    Screen.Task,
                    Screen.Idea,
                    Screen.Settings
                ),
                navController = navController
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = if (isUserLoggedIn) Destination.Home else Destination.Auth,
            modifier = Modifier.padding(padding)
        ) {
            authGraph(navController, onLoginSuccess)
            mainGraph(navController, isRefreshUI) { isRefreshUi ->
                onRefreshUI(isRefreshUi)
            }
        }
    }
}

private fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    navigation<Destination.Auth>(
        startDestination = Destination.Login
    ) {
        composable<Destination.Login> {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(Destination.Home) {
                        popUpTo(Destination.Auth) {
                            inclusive = true
                        }
                    }
                    onLoginSuccess()
                },
                onRegisterClick = {
                    navController.navigate(Destination.Register) {
                        popUpTo(Destination.Login) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }

        composable<Destination.Register> {
            RegisterScreenRoot(
                onNavigateToLoginClick = {
                    navController.navigate(Destination.Login) {
                        popUpTo(Destination.Register) {
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

private fun NavGraphBuilder.mainGraph(
    navController: NavController,
    isRefreshUI: Boolean,
    onRefreshUI: (Boolean) -> Unit,
) {
    navigation<Destination.Home>(
        startDestination = Destination.Task
    ) {
        composable<Destination.Task> {
            println("!! MainGraph:  onRefreshUI is $isRefreshUI")
            val viewModel = koinViewModel<TaskViewModel>()

            TaskScreenRoot(
                isRefreshUi = isRefreshUI
            )
        }
        composable<Destination.Idea> {
            // updating list after coming back from addIdea screen..
            val backStackEntryResult by navController.currentBackStackEntryAsState()
            val result = backStackEntryResult?.savedStateHandle?.getLiveData<Boolean>("invalidate")

            IdeaScreenRoot(
                onAddIdeaClick = {
                    navController.navigate(Destination.AddIdea)
                },
                isRefreshUI = {
                    onRefreshUI(true)
                },
                invalidateList = result
            )
        }
        composable<Destination.Settings> {
            SettingsScreenRoot()
        }
        composable<Destination.AddIdea> {
            AddIdeaScreenRoot(
                onBackClick = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle?.set("invalidate", true)
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val destination: Any, val label: String, val icon: ImageVector) {
    data object Task : Screen(
        destination = Destination.Task,
        label = "Home",
        icon = Icons.Default.Home
    )

    data object Idea : Screen(
        destination = Destination.Idea,
        label = "Idea Pool",
        icon = Icons.Default.ShoppingCart
    )

    data object Settings :
        Screen(
            destination = Destination.Settings,
            label = "Settings",
            icon = Icons.Default.Settings
        )
}