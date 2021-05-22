package com.app.newsapp.ui.details

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.app.newsapp.R
import com.app.newsapp.data.local.NewsArticleDb
import com.app.newsapp.databinding.FragmentNewsDetailsBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsDetailsFragment: Fragment() {

    private lateinit var dataBinding: FragmentNewsDetailsBinding
    private lateinit var newsArticleDb: NewsArticleDb


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments


        newsArticleDb = bundle?.getParcelable("newsArticleDb")!!
        dataBinding.item = newsArticleDb

        dataBinding.tvReadMore.setOnClickListener {
            CustomTabsIntent.Builder().build().launchUrl(requireContext(), Uri.parse(newsArticleDb.url))
        }

        dataBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

}