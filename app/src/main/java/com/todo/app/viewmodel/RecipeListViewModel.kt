package com.todo.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.app.model.api.Resource
import com.todo.app.model.domainmodel.ItemDomainModel
import com.todo.app.model.domainmodel.RecipeDomainModel
import com.todo.app.repository.RecipeRepository
import com.todo.retrofitmodule.dto.ItemResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    val itemResponse: MutableLiveData<Resource<List<RecipeDomainModel>>> = MutableLiveData()

    fun searchRecipe(query: String) = viewModelScope.launch(Dispatchers.IO) {
        safeRecipeResponseCall(query)
    }

    private suspend fun safeRecipeResponseCall(query: String) {
        itemResponse.postValue(Resource.Loading())
        try {
            val response = recipeRepository.getItems(query)
            itemResponse.postValue(handleResponse(response))
        } catch (error: Exception) {
            when (error) {
                is IOException -> itemResponse.postValue(Resource.Error("Network failure"))
                else -> itemResponse.postValue(Resource.Error("Error in data conversion"))
            }
        }
    }

    private fun handleResponse(response: Response<ItemResponse>): Resource<List<RecipeDomainModel>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                val recipeList = mutableListOf<RecipeDomainModel>()
                for (item in ItemDomainModel(resultResponse).hits) {
                    recipeList.add(item.recipe)
                }
                return Resource.Success(recipeList.sortedBy { it.label })
            }
        }
        return Resource.Error(response.message())
    }

    fun getSavedRecipes(query: String) = recipeRepository.getSavedRecipe(query)
}