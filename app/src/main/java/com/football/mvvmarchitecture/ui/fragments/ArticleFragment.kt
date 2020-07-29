package com.football.mvvmarchitecture.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.football.mvvmarchitecture.R
import com.football.mvvmarchitecture.ui.MainActivity
import com.football.mvvmarchitecture.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel : NewsViewModel
    val args: ArticleFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val  article = args.article

        webView.apply {
            webChromeClient = WebChromeClient()
            loadUrl(article.url)
        }

        fab.setOnClickListener{
            viewModel.savedArticle(article)
            Toast.makeText(activity, "Haber Kaydedildi", Toast.LENGTH_SHORT).show()
        }


    }
}