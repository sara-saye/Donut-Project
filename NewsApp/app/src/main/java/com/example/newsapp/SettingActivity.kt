package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.navBtn.selectedItemId=R.id.setting
        binding.navBtn.setOnItemSelectedListener {
            val i:Intent
            when (it.itemId) {
                R.id.setting -> {}
                R.id.home -> {
                    i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                }
                R.id.favorite->{
                    i= Intent(this, FavoriteActivity::class.java)
                    startActivity(i)
                }
                else->{}
            }
            true
        }
        binding.usBtn.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.yemenBtn.setOnClickListener {
            //val intent=Intent(this,ArabicNewsActivity::class.java)
            startActivity(intent)
        }
    }
}