package com.football.mvvmarchitecture.api

import com.football.mvvmarchitecture.models.NewsResponse
import com.football.mvvmarchitecture.utils.Constans.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi  {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode  :String= "tr",
        @Query("pages")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String=API_KEY
    ): Response<NewsResponse>


    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String= "",
        @Query("pages")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String=API_KEY
    ): Response<NewsResponse>






}