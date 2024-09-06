package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private var selectedCountry: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.navBtn.selectedItemId = R.id.setting
        binding.navBtn.setOnItemSelectedListener {
            val i: Intent
            when (it.itemId) {
                R.id.setting -> {}
                R.id.home -> {
                    i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                }

                R.id.favorite -> {
                    i = Intent(this, FavoriteActivity::class.java)
                    startActivity(i)
                }

                else -> {}
            }
            true
        }
        binding.logout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("login",false)
            editor.apply()
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        changeCountry()
    }
    private fun changeCountry() {
        val countries = listOf(
            "Select Country",
            "us",
            "eg",
            "de"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countrySpinner.adapter = adapter

        binding.countrySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // selectedCountry = parent.getItemAtPosition(position).toString()
                    // Handle the case where the user selects the first item ("Select Country")
                    val item = parent.getItemAtPosition(position)
                    selectedCountry = item?.toString() ?: ""
                    if (selectedCountry == "Select Country") {
                        selectedCountry = ""
                    } else {
                        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("Country", selectedCountry)
                        editor.apply()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }
}