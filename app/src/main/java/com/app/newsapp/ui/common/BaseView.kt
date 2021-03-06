package com.app.newsapp.ui.common

import androidx.annotation.UiThread

interface BaseView {

    fun onInflateLayout(): Int

    @UiThread
    fun showProgress(resourceId: Int)

    @UiThread
    fun hideProgress()

}