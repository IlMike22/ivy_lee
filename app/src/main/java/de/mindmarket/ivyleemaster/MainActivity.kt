package de.mindmarket.ivyleemaster

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import org.koin.androidx.compose.koinViewModel

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
                    val viewModel = koinViewModel<MainViewModel>()
                    val isRefreshUI by viewModel.isRefreshUI.collectAsStateWithLifecycle()
                    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsStateWithLifecycle()

                    NavigationRoot(
                        navController,
                        isUserLoggedIn,
                        onLoginSuccess = {
                            viewModel.checkLoginState()
                        },
                        isRefreshUI = isRefreshUI,
                        onRefreshUI = { refreshUI ->
                            viewModel.setRefreshUIState(refreshUI)
                        }
                    )
                }
            }
        }
    }
}

