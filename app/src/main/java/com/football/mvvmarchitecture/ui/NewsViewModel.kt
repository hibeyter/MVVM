package com.football.mvvmarchitecture.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.football.mvvmarchitecture.BaseApplication
import com.football.mvvmarchitecture.models.Article
import com.football.mvvmarchitecture.models.NewsResponse
import com.football.mvvmarchitecture.repository.NewsRepository
import com.football.mvvmarchitecture.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(val app: Application ,val newsRepository:NewsRepository) : AndroidViewModel(app) {

    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage =1
    var breakingNewsResponse : NewsResponse? =null



    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage =1
    var searchNewsResponse : NewsResponse?=null

    init {
        getBreakingNews("tr")
    }

    fun getBreakingNews(countryCode:String) =viewModelScope.launch {
        safeBreakingNewsCall(countryCode);
    }

    fun searchNews(query:String) =viewModelScope.launch {
       safeSearchNewsCall(query)
    }
    private  fun handleResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                breakingNewsPage++
                if(breakingNewsResponse == null) {
                    breakingNewsResponse = it
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Succses(breakingNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    private  fun handleSearchResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                searchNewsPage++
                if(searchNewsResponse == null) {
                    searchNewsResponse = it
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Succses(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun savedArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private suspend fun safeBreakingNewsCall(countryCode: String){
        breakingNews.postValue(Resource.Loading())
        try {
            if(hasIntenetconnection()){
                val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
                breakingNews.postValue(handleResponse(response))
            }else breakingNews.postValue(Resource.Error("İnternet Bağlantısı yok"))
        }catch (t: Throwable){
                when(t){
                    is IOException -> breakingNews.postValue(Resource.Error("Ağ hatası"))
                    else -> breakingNews.postValue(Resource.Error("Upsss."))
                }
        }
    }

    private suspend fun safeSearchNewsCall(query: String){
        searchNews.postValue(Resource.Loading())
        try {
            if(hasIntenetconnection()){
                val response = newsRepository.searchNews(query,searchNewsPage)
                searchNews.postValue(handleSearchResponse(response))
            }else searchNews.postValue(Resource.Error("İnternet Bağlantısı yok"))
        }catch (t: Throwable){
            when(t){
                is IOException -> searchNews.postValue(Resource.Error("Ağ hatası"))
                else -> searchNews.postValue(Resource.Error("Upsss."))
            }
        }
    }
    private fun hasIntenetconnection():Boolean {
        val connectivityManager = getApplication<BaseApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilies =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilies.hasTransport(TRANSPORT_WIFI) -> true
                capabilies.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilies.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> return false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false

    }


}