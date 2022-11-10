package com.gvelesiani.socialx.compose

import android.util.Log.d
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gvelesiani.socialx.compose.components.ProgressIndicator
import com.gvelesiani.socialx.presentation.home.HomeUiState
import com.gvelesiani.socialx.presentation.home.HomeVM

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeVM = hiltViewModel()) {
    d("homeScreen---", "debug-home-screen")
    Text("asdkjfahsjkdf")
    val uiState = viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState.value) {
            is HomeUiState.Empty -> {}
            is HomeUiState.Error -> {}
            is HomeUiState.Loading -> {
//                ProgressIndicator(isDisplayed = true)
            }

            is HomeUiState.PostSuccess -> {
                PostContent(posts = state.posts)
                ProgressIndicator(isDisplayed = false)
            }

            is HomeUiState.UserInfoSuccess ->{}
            is HomeUiState.StoriesSuccess -> {}
        }
    }
}