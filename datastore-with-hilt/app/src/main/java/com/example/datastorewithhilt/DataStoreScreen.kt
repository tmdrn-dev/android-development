package com.example.datastorewithhilt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun DataStoreScreen(
    viewModel: DataStoreViewModel = hiltViewModel()
) {
    val userDataState = viewModel.data.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier) {
            Text(
                text = "name = ${userDataState.value.name}, birth = ${userDataState.value.birth}",
                fontSize = 20.sp,
                modifier = Modifier
            )
            Button(
                onClick = {
                    viewModel.setName("SK")
                    viewModel.setBirth("88")
                },
                modifier = Modifier
            ) {
                Text(
                    text = "Input Name"
                )
            }
        }
    }
}