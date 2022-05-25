package com.example.androidtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidtest.databinding.ActivityMainBinding
import com.example.androidtest.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Repositories.init(applicationContext)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerMain, MainFragment())
            .commit()
    }
}
