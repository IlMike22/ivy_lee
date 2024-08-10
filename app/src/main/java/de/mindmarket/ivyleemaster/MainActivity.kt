package de.mindmarket.ivyleemaster

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import de.mindmarket.ivyleemaster.core.presentation.components.IvyBottomAppBar
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IvyLeeMasterTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
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
                    ) {
                        NavigationRoot(
                            navController,
                            isLoggedIn = false
                        )
                    }
                }
            }
        }
    }
}