package com.example.datastorewithhilt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastorewithhilt.data.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.datastorewithhilt.data.repository.UserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: UserDataRepository
): ViewModel() {

    val data = repository.userDataFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserData("UNKNOWN","UNKNOWN"),
        )

    fun setName(name: String) {
        viewModelScope.launch {
            repository.setName(name)
        }
    }

    fun setBirth(birth: String) {
        viewModelScope.launch {
            repository.setBirth(birth)
        }
    }
}
