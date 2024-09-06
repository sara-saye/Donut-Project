package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {
    @GET("/search-news?source-countries=us,eg,de&categories=education,business,culture&api-key=219080b69dda4dd88f7fd377a31a171b&number=100")
    fun getNews(): Call<News>
}