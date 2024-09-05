package com.example.newsapp
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    object favoriteNews :ArrayList<Article>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding=ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = NewsAdapter(favoriteNews)
        binding.newslist.adapter = adapter
        binding.navBtn.selectedItemId=R.id.favorite
        binding.navBtn.setOnItemSelectedListener {
            val i:Intent
            when (it.itemId) {
                R.id.setting -> {
                    i= Intent(this, SettingActivity::class.java)
                    startActivity(i)
                }
                R.id.home -> {
                    i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                }
                R.id.favorite->{}
                else->{}
            }
            true
        }
    }
}