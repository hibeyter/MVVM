package com.football.mvvmarchitecture.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.football.mvvmarchitecture.R
import com.football.mvvmarchitecture.db.ArticleDatabase
import com.football.mvvmarchitecture.repository.NewsRepository
import com.football.mvvmarchitecture.utils.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = NewsRepository(ArticleDatabase(this))
        val  viewModelProviderFactory =
            ViewModelProviderFactory(
                application,
                repository
            )
        viewModel= ViewModelProvider(this,viewModelProviderFactory)
            .get(NewsViewModel::class.java)

        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

    }
}