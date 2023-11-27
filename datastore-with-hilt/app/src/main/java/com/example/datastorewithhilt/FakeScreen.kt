package com.example.datastorewithhilt

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FakeScreen(
    viewModel: FakeViewModel = hiltViewModel()
) {
    val data = viewModel.data.collectAsState()

    Text(text = data.value?.data.toString())
}