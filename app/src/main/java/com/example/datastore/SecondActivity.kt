package com.example.datastore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.emptyPreferences
import androidx.lifecycle.lifecycleScope
import com.example.datastore.databinding.ActivitySecondBinding
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class SecondActivity : AppCompatActivity() {

    companion object {
        private val TAG = SecondActivity::class.java.simpleName

        internal fun getIntent(context: Context) : Intent {
            return Intent(context, SecondActivity::class.java)
        }
    }


    private val dataStore: DataStore<Preferences> = createDataStore(name = "user")

    //**********************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            loadUser()
        }
    }

    //**********************************************************************************************
    private suspend fun loadUser() {

        val userFlow: Flow<UserModel> = dataStore.data
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

        userFlow.collectLatest { userModel ->
            showUserInfo(userModel)
        }

    }

    //**********************************************************************************************
    private fun showUserInfo(userModel: UserModel) {
        textName.text = userModel.name
        textFamily.text = userModel.family
    }

}