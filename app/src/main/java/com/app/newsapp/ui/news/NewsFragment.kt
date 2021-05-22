package com.app.newsapp.ui.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.newsapp.BR
import com.app.newsapp.R
import com.app.newsapp.data.local.NewsArticleDb
import com.app.newsapp.databinding.FragmentNewsBinding
import com.app.newsapp.ui.common.BaseFragment
import com.app.newsapp.ui.details.NewsDetailsFragment
import com.app.newsapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class NewsFragment:BaseFragment<FragmentNewsBinding, NewsViewModel>(), ItemClickListener {

    private val newsViewModel: NewsViewModel by viewModels()

    private lateinit var bindingAdapter: RecyclerViewDataBindingAdapter<NewsArticleDb>
    private var listOfArticles = ArrayList<NewsArticleDb>()

    override fun onInflateLayout(): Int {
        return R.layout.fragment_news
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        newsViewModel.getNews()

        newsViewModel.newsData.observe(viewLifecycleOwner, EventObserver{
            if(it.isNotEmpty()){
                listOfArticles = it as ArrayList
                dataBinding.rvNewsData.visibility = View.VISIBLE
                dataBinding.rvNewsData.layoutManager = LinearLayoutManager(context)
                val map = HashMap<Int, Any>()
                map[BR.listener] = this
                bindingAdapter = RecyclerViewDataBindingAdapter(R.layout.row_news, listOfArticles, map)

                dataBinding.rvNewsData.adapter = bindingAdapter
            } else {
                dataBinding.rvNewsData.visibility = View.GONE
                CommonUtils.showToast(requireContext(), getString(R.string.error_news_data_not_available))
            }

        })

        newsViewModel.state.observe(viewLifecycleOwner, EventObserver{
            when(it) {
                is SuccessState -> {
                    hideProgress()
                }

                is ErrorState -> {
                    hideProgress()
                    CommonUtils.showToast(requireContext(), it.error.message!!)
                }

                is LoadingState -> {
                    showProgress(R.layout.custom_progress)
                }
            }
        })

    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(listOfArticles[position]))
    }


}