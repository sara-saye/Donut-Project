package com.example.newsapp
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.newsapp.databinding.ActivityHomeBinding
//import com.facebook.shimmer.ShimmerFrameLayout
//import com.google.android.gms.ads.MobileAds
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadNews()
      //  MobileAds.initialize(this)
//        val banner_ad_layout = findViewById<ShimmerFrameLayout>(R.id.banner_layout)
//
//        BannerAds.createBanner(this, banner_ad_layout, this@HomeActivity)
       binding.navBtn.selectedItemId=R.id.home
        binding.navBtn.setOnItemSelectedListener {
            val i:Intent
            when (it.itemId) {
                R.id.setting -> {
                    i = Intent(this, SettingActivity::class.java)
                    startActivity(i)
                }
                R.id.favorite -> {
                    i = Intent(this, FavoriteActivity::class.java)
                    startActivity(i)
                }
                R.id.home->{}

            }
            true
        }
    }

    private fun loadNews() {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.worldnewsapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val c = retrofit.create(NewsCallable::class.java)
        c.getNews().enqueue(object : Callback<News> {
            override fun onResponse(p0: Call<News>, p1: Response<News>) {
                val newss = p1.body()
                val news = newss?.news!!
                showNews(news)
                binding.loading.isVisible = false
                Log.d("trace", news.toString())
            }

            override fun onFailure(p0: Call<News>, p1: Throwable) {
                Log.e("trace", "Failed to load news", p1)
            }
        })
    }

    private fun showNews(news: ArrayList<Article>) {
        val adapter = NewsAdapter(news)
        binding.newslist.adapter = adapter
    }
}