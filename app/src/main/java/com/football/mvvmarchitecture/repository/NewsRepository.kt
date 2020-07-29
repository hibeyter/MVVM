package com.football.mvvmarchitecture.repository

import com.football.mvvmarchitecture.api.RetrofitInstance
import com.football.mvvmarchitecture.db.ArticleDatabase
import com.football.mvvmarchitecture.models.Article

class NewsRepository( val db:ArticleDatabase) {

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews()

    suspend fun searchNews(query:String, pageNumber: Int)=
        RetrofitInstance.api.searchForNews(query,pageNumber)

    suspend fun upsert(article:Article) =
        db.getArticles().upsert(article)

     fun getArticles()= db.getArticles().getAllArticles()

    suspend fun deleteArticle(article: Article)= db.getArticles().deleteArticle(article)

}