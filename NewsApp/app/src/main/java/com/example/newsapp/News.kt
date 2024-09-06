package com.example.newsapp

data class News(
    val news: ArrayList<Article>
)
data class Article(
    val title:String,
    val url:String,
    val image:String,
    val catgory:String,
   val source_country:String
)
