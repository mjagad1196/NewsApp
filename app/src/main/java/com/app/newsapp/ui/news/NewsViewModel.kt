package com.app.newsapp.ui.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.newsapp.data.local.NewsArticleDb
import com.app.newsapp.domain.repository.AppRepository
import com.app.newsapp.utils.*
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(private val appRepository: AppRepository): ViewModel(){


    private var _news = MutableLiveData<Event<List<NewsArticleDb>>>()
    var newsData: LiveData<Event<List<NewsArticleDb>>> = _news

    private var _state = MutableLiveData<Event<State>>()
    var state: LiveData<Event<State>> = _state

    fun getNews() {
        viewModelScope.launch {
            _state.postValue(Event(LoadingState()))

            appRepository.getNewsFromCache().let { operationResult ->
                when(operationResult){
                    is OperationResult.Success -> {
                        _state.postValue(Event(SuccessState()))
                        _news.postValue(Event(operationResult.data))
                    }

                    is OperationResult.Error -> {
                        _state.postValue(Event(ErrorState(operationResult.exception)))
                    }
                }
            }
        }
    }

}