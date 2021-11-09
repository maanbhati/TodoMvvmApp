package com.todo.app.repository

import com.todo.app.model.domainmodel.RecipeDomainModel
import com.todo.app.model.roomdb.RecipeDao
import com.todo.retrofitmodule.api.Api
import com.todo.retrofitmodule.dto.ItemResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val api: Api,
    private val recipeDao: RecipeDao
) {
    suspend fun getItems(query: String): Response<ItemResponse> = api.getItems(query)

    fun getSavedRecipe(query: String) = recipeDao.getSavedRecipe(query)

    suspend fun insertRecipe(recipe: RecipeDomainModel) = recipeDao.insert(recipe)

    suspend fun insertAllRecipes(recipes: List<RecipeDomainModel>) = recipeDao.insertAll(recipes)

    suspend fun deleteRecipe(recipe: RecipeDomainModel) = recipeDao.delete(recipe)

    suspend fun deleteAllRecipe() = recipeDao.deleteAllRecipe()
}