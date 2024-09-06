package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsCallable {
    @GET("/search-news?source-countries=us,eg,de&categories=education,business,culture&api-key=85ddb0491b684b5b9388e5de43f8df27&number=100")
    fun getNews(): Call<News>
}