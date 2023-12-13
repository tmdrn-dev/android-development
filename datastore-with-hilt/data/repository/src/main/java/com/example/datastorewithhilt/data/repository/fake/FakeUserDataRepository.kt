package com.example.datastorewithhilt.data.repository.fake

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastorewithhilt.data.model.UserData
import com.example.datastorewithhilt.data.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeUserDataRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
): UserDataRepository {
    private object PreferencesKeys {
        val NAME = stringPreferencesKey("name")
        val BIRTH = stringPreferencesKey("birth")
    }

    override val userDataFlow: Flow<UserData> = dataStore.data
        .map {
            val name = it[PreferencesKeys.NAME] ?: ""
            val birth = it[PreferencesKeys.BIRTH] ?: ""
            UserData(name, birth)
        }

    override suspend fun setName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NAME] = name
        }
    }

    override suspend fun setBirth(birth: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BIRTH] = birth
        }
    }
}