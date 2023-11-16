package com.example.datastorewithhilt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.datastorewithhilt.ui.theme.DatastoreWithHiltTheme
import com.example.datastorewithhilt.data.model.UserData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatastoreWithHiltTheme {
                val viewModel: MainActivityViewModel by viewModels()
                val userDataState = viewModel.data.collectAsStateWithLifecycle()
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier) {
//                    Greeting("Android")
                        Text(
                            text = "name = ${userDataState.value.name}, birth = ${userDataState.value.birth}",
                            fontSize = 20.sp,
                            modifier = Modifier
                        )
                        Button(
                            onClick = {
                                viewModel.setName("YN")
                                viewModel.setBirth("19")
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
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DatastoreWithHiltTheme {
        Greeting("Android")
    }
}