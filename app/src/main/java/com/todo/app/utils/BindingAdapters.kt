package com.todo.app.utils

import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("recipeImage")
    fun loadImage(view: AppCompatImageView, url: String) = view.loadImage(url)

    @BindingAdapter("loadUrl")
    @JvmStatic
    fun loadUrl(view: WebView, url: String) {
        view.loadUrl(url)
    }

    @BindingAdapter("setProgressBar")
    @JvmStatic
    fun setProgressBar(view: ProgressBar, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}