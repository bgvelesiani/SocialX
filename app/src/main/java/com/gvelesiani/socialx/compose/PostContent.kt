package com.gvelesiani.socialx.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.gvelesiani.socialx.api.Post
import com.gvelesiani.socialx.compose.PostItem

@Composable
fun PostContent(posts: List<Post>) {
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
        items(posts) { post ->
            PostItem(post)
        }
    }
}