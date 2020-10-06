package com.example.datastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.datastore.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val dataStore: DataStore<Preferences> = createDataStore(name = "user")

    //**********************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonSave.setOnClickListener {
            saveUser(
                editName.text.toString(),
                editFamily.text.toString()
            )
        }

        buttonGoToSecondPage.setOnClickListener {
            startActivity(SecondActivity.getIntent(this))
        }

    }

    //**********************************************************************************************
    private fun saveUser(name: String, family: String) {

        lifecycleScope.launch {

            dataStore.edit { preferences ->
                preferences[UserPreferencesKeys.NAME] = name
                preferences[UserPreferencesKeys.FAMILY] = family
            }

        }

    }

}