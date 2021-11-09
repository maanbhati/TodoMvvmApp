package com.todo.app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.todo.app.model.domainmodel.RecipeDomainModel
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
class DetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: RecipeRepository

    @MockK
    private lateinit var recipeDomainModel: RecipeDomainModel

    @MockK
    private lateinit var customWebViewClient: DetailViewModel.CustomWebViewClient

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun when_save_recipe_called_verify_method_call_from_repository() = runBlockingTest {
        viewModel.saveRecipe(recipeDomainModel)

        verify { runBlockingTest { repository.insertRecipe(recipeDomainModel) } }
    }

    @Test
    fun when_fet_saved_recipe_called_do_not_call_delete_method_from_repository() =
        runBlockingTest {
            viewModel.saveRecipe(recipeDomainModel)

            verify(inverse = true) { runBlockingTest { repository.deleteRecipe(recipeDomainModel) } }
        }

    @Test
    fun when_get_web_view_client_called_verify_instance_is_of_custom_web_view_client() {
        assert(viewModel.getWebViewClient() is DetailViewModel.CustomWebViewClient)
    }
}