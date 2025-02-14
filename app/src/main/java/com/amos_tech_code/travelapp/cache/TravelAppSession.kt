package com.amos_tech_code.travelapp.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class TravelAppSession(private val dataStore: DataStore<Preferences>) {

    private val tokenKey = stringPreferencesKey("token")
    private val userNameKey = stringPreferencesKey("username")

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[tokenKey] = token
        }

    }

    suspend fun saveUserName(userName: String) {
        dataStore.edit {
            it[userNameKey] = userName
        }
    }

    suspend fun saveToken(token: String){

    }

    suspend fun getToken(): String? {
        return dataStore.data.map {
            it[tokenKey]
        }.firstOrNull()
    }

    suspend fun getUserName(): String? {
        return dataStore.data.map {
            it[userNameKey]
        }.firstOrNull()
    }

    suspend fun clearSession() {
        dataStore.edit { it.clear() }
    }
}