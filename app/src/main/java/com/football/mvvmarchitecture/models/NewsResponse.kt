package com.football.mvvmarchitecture.models

import com.football.mvvmarchitecture.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)