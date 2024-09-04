package com.example.newsapp

data class Category(
    var news: ArrayList<News>
)

data class News(
    var articales: ArrayList<Articale>
)

data class Articale(
    val ss: String
)