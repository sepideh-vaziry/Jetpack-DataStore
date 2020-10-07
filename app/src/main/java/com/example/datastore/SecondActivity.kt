package com.example.datastore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.datastore.databinding.ActivitySecondBinding
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    companion object {

        internal fun getIntent(context: Context) : Intent {
            return Intent(context, SecondActivity::class.java)
        }

    }

    private lateinit var userDataStore: UserDataStore

    //**********************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDataStore = UserDataStore(this)

        lifecycleScope.launch {
            loadUser()
        }
    }

    //**********************************************************************************************
    private suspend fun loadUser() {

        userDataStore.getUser().collectLatest { userModel ->
            showUserInfo(userModel)
        }

    }

    //**********************************************************************************************
    private fun showUserInfo(userModel: UserModel) {
        textName.text = userModel.name
        textFamily.text = userModel.family
    }

}