package com.football.mvvmarchitecture.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.football.mvvmarchitecture.repository.NewsRepository
import com.football.mvvmarchitecture.ui.NewsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory(val app: Application,val newsRepository: NewsRepository) : ViewModelProvider.Factory{


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app,
            newsRepository
        ) as T
    }
}