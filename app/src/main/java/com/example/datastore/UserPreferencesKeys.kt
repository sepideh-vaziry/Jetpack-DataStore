package com.example.datastore

import androidx.datastore.preferences.preferencesKey

object UserPreferencesKeys {

    val NAME = preferencesKey<String>("name")
    val FAMILY = preferencesKey<String>("family")

}