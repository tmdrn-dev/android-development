package com.example.datastorewithhilt

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FakeScreen(
    viewModel: FakeViewModel = hiltViewModel()
) {
    val data = viewModel.data.collectAsStateWithLifecycle()
    Text(text = data.value.toString())
}