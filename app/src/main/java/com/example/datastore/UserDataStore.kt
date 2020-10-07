package com.example.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserDataStore(context: Context) {

    companion object {
        private val TAG = UserDataStore::class.java.simpleName

        private const val DATA_STORE_NAME = "user"
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = DATA_STORE_NAME)

    //**********************************************************************************************
    internal suspend fun saveUser(name: String, family: String) {

        dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.NAME] = name
            preferences[UserPreferencesKeys.FAMILY] = family
        }

    }

    //**********************************************************************************************
    internal suspend fun getUser() : Flow<UserModel> {

        return dataStore.data
            .catch { exception ->

                if (exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    Log.e(TAG, "loadUser: ", exception)
                }

            }
            .map { preferences ->
                val name = preferences[UserPreferencesKeys.NAME]
                val family = preferences[UserPreferencesKeys.FAMILY]

                UserModel().apply {
                    this.name = name
                    this.family = family
                }
            }
    }

}