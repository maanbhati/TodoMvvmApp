package com.todo.app.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.todo.app.databinding.FragmentRecipeListBinding
import com.todo.app.model.api.Resource
import com.todo.app.model.domainmodel.RecipeDomainModel
import com.todo.app.utils.hasInternetConnection
import com.todo.app.view.adapter.RecipeListAdapter
import com.todo.app.viewmodel.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListFragment : Fragment(), RecipeListAdapter.OnItemClickListener {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelRecipe: RecipeListViewModel
    private lateinit var recipeListAdapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelRecipe = ViewModelProvider(this)[RecipeListViewModel::class.java]
        recipeListAdapter = RecipeListAdapter(this)

        binding.apply {
            rvListItems.adapter = recipeListAdapter
        }

        var job: Job? = null
        binding.editSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    val query = editable.toString()
                    if (query.isNotEmpty()) {
                        observeData(query)
                    }
                }
            }
        }
    }

    private fun observeData(query: String) {
        if (hasInternetConnection(requireContext())) {
            viewModelRecipe.searchRecipe(query)
            observeItemResponse()
        } else {
            observeSavedData(query)
        }
    }

    private fun observeItemResponse() {
        viewModelRecipe.itemResponse.observe(viewLifecycleOwner) { it ->
            when (it) {
                is Resource.Success -> {
                    shouldShowProgressBar(binding.progressBar, false)
                    it.data?.let { itemResponse ->
                        recipeListAdapter.submitList(itemResponse)
                    }
                }
                is Resource.Error -> {
                    shouldShowProgressBar(binding.progressBar, false)
                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        Log.e("ListFragment", message)
                    }
                }
                is Resource.Loading -> {
                    shouldShowProgressBar(binding.progressBar, true)
                }
            }
        }
    }

    private fun shouldShowProgressBar(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun observeSavedData(query: String) {
        viewModelRecipe.getSavedRecipes(query).observe(viewLifecycleOwner) { recipe ->
            recipeListAdapter.submitList(recipe)
            shouldShowProgressBar(binding.progressBar, false)
        }
    }

    override fun onItemClick(recipe: RecipeDomainModel) {
        val action =
            RecipeListFragmentDirections.actionListFragmentToDetailFragment(recipe)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}