package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import de.mindmarket.ivyleemaster.Screen

@Composable
fun IvyBottomAppBar(
    screens: List<Screen>,
    navController: NavController,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    if (isVisible) {
        BottomAppBar(
            actions = {
                screens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.destination,
                        onClick = {
                            if (currentRoute != screen.destination) {
                                navController.navigate(screen.destination) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = screen.label,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
        )
    }
}