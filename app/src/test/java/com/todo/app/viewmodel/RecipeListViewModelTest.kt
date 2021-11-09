package com.todo.app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.todo.app.repository.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RecipeListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: RecipeRepository

    private lateinit var viewModel: RecipeListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = RecipeListViewModel(repository)
    }

    @Test
    fun when_get_saved_recipe_called_verify_method_call_from_repository() = runBlockingTest {
        val query = "query"
        viewModel.getSavedRecipes(query)

        verify { repository.getSavedRecipe(query) }
    }

    @Test
    fun when_search_recipe_called_verify_method_call_from_repository() = runBlockingTest {
        val query = "query"
        viewModel.searchRecipe(query)

        Thread.sleep(500L)
        verify { runBlockingTest { repository.getItems(query) } }
    }

    @Test
    fun when_get_save_recipe_called_do_not_call_api_method_from_repository() =
        runBlockingTest {
            val query = "query"
            viewModel.getSavedRecipes(query)

            verify(inverse = true) { runBlockingTest { repository.getItems(query) } }
        }
}