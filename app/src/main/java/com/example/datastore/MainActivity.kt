package com.example.datastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.datastore.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var userDataStore: UserDataStore

    //**********************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDataStore = UserDataStore(this)

        buttonSave.setOnClickListener {
            lifecycleScope.launch {
                userDataStore.saveUser(
                    editName.text.toString(),
                    editFamily.text.toString()
                )
            }
        }

        buttonGoToSecondPage.setOnClickListener {
            startActivity(SecondActivity.getIntent(this))
        }

    }

}