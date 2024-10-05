package de.mindmarket.ivyleemaster

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import de.mindmarket.ivyleemaster.add_idea.presentation.AddIdeaScreenRoot
import de.mindmarket.ivyleemaster.auth.login.presentation.LoginScreenRoot
import de.mindmarket.ivyleemaster.auth.register.presentation.RegisterScreenRoot
import de.mindmarket.ivyleemaster.core.presentation.components.IvyBottomAppBar
import de.mindmarket.ivyleemaster.core.presentation.navigator.Destination
import de.mindmarket.ivyleemaster.core.presentation.navigator.NavigationAction
import de.mindmarket.ivyleemaster.core.presentation.navigator.Navigator
import de.mindmarket.ivyleemaster.core.presentation.util.ObserveAsEvents
import de.mindmarket.ivyleemaster.idea.presentation.IdeaScreenRoot
import de.mindmarket.ivyleemaster.idea.presentation.IdeaViewModel
import de.mindmarket.ivyleemaster.settings.SettingsScreenRoot
import de.mindmarket.ivyleemaster.task.presentation.TaskScreenRoot
import de.mindmarket.ivyleemaster.util.presentation.Route
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    val navigator = koinInject<Navigator>()

    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.Navigate ->
                navController.navigate(
                    action.destination
                ) {
                    action.navOptions(this)
                }

            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }

    Scaffold(
        bottomBar = {
            IvyBottomAppBar(
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
            startDestination = navigator.startDestination,
            modifier = Modifier.padding(padding)
        ) {
            authGraph(navController)
            mainGraph(navController)
        }
    }
}

private fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<Destination.AuthGraph>(
        startDestination = Destination.Login
    ) {
        composable<Destination.Login> {
            LoginScreenRoot(
                onLoginSuccess = {},
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

        composable<Destination.Register> {
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
    navigation<Destination.HomeGraph>(
        startDestination = Destination.Task
    ) {
        composable<Destination.Task> {
            TaskScreenRoot()
        }
        composable<Destination.Idea> {
            val viewModel = koinViewModel<IdeaViewModel>()
            val state by viewModel.state.collectAsState()
            IdeaScreenRoot(
                state = state,
                onAction = viewModel::onAction,
                onAddIdeaClick = {}
            )
        }
        composable<Destination.Settings> {
            SettingsScreenRoot()
        }
        composable<Destination.AddIdea> {
            AddIdeaScreenRoot(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val destination: Destination, val label: String, val icon: ImageVector) {
    data object Task : Screen(destination = Destination.Task, label = "Home", icon = Icons.Default.Home)
    data object Idea :
        Screen(destination = Destination.Idea, label = "Idea Pool", icon = Icons.Default.ShoppingCart)

    data object Settings :
        Screen(destination = Destination.Settings, label = "Settings", icon = Icons.Default.Settings)
}