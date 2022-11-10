package com.gvelesiani.socialx.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gvelesiani.socialx.navGraph.Screen
import com.gvelesiani.socialx.presentation.login.LoginUiState
import com.gvelesiani.socialx.presentation.login.LoginVM

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginVM = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Button(onClick = { viewModel.loginUser("bacho2@gmail.com", "bachiko123") }) {
            Text("Login User")
        }

        when (val state = uiState.value) {
            is LoginUiState.Empty -> {}
            is LoginUiState.Error -> {}
            is LoginUiState.Loading -> {
//                ProgressIndicator(isDisplayed = true)
            }
            is LoginUiState.Success -> {
                navController.navigate(Screen.Home.route)
            }
        }
    }
}