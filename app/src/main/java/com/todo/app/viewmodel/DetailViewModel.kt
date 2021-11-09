package com.todo.app.viewmodel

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.app.model.domainmodel.RecipeDomainModel
import com.todo.app.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    val isProgressVisible = ObservableBoolean()

    fun saveRecipe(recipeDomainModel: RecipeDomainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepository.insertRecipe(recipeDomainModel)
        }
    }

    fun getWebViewClient(): WebViewClient {
        return CustomWebViewClient()
    }

    inner class CustomWebViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            isProgressVisible.set(true)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url != null) {
                view?.loadUrl(url)
            }
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            isProgressVisible.set(false)
        }
    }
}