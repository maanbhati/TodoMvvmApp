package com.todo.app.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.todo.app.model.domainmodel.RecipeDomainModel
import com.todo.app.model.roomdb.RecipeDao
import com.todo.retrofitmodule.api.Api
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RecipeRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var api: Api

    @MockK
    private lateinit var recipeDao: RecipeDao

    @MockK
    private lateinit var recipeDomainModel: RecipeDomainModel

    private lateinit var repository: RecipeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        repository = RecipeRepository(api, recipeDao)
    }

    @Test
    fun when_get_all_recipe_called_verify_method_call_from_dao() {
        val query = "query"
        repository.getSavedRecipe(query)

        verify { recipeDao.getSavedRecipe(query) }
    }

    @Test
    fun when_get_item_called_verify_method_call_from_api() = runBlockingTest {
        val query = "query"
        repository.getItems(query)

        verify { runBlockingTest { api.getItems(query) } }
    }

    @Test
    fun when_insert_recipe_called_verify_method_call_from_dao() = runBlockingTest {
        repository.insertRecipe(recipeDomainModel)

        verify { runBlockingTest { recipeDao.insert(recipeDomainModel) } }
    }

    @Test
    fun when_insert_all_recipe_called_verify_method_call_from_dao() = runBlockingTest {
        val recipeList = listOf(recipeDomainModel)
        repository.insertAllRecipes(recipeList)

        verify { runBlockingTest { recipeDao.insertAll(recipeList) } }
    }

    @Test
    fun when_delete_recipe_called_verify_method_call_from_dao() = runBlockingTest {
        repository.deleteRecipe(recipeDomainModel)

        verify { runBlockingTest { recipeDao.delete(recipeDomainModel) } }
    }

    @Test
    fun when_delete_all_recipe_called_verify_method_call_from_dao() = runBlockingTest {
        repository.deleteAllRecipe()

        verify { runBlockingTest { recipeDao.deleteAllRecipe() } }
    }
}