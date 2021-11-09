package com.todo.app.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.todo.app.databinding.FragmentRecipeDetailBinding
import com.todo.app.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelDetail: DetailViewModel
    private val args by navArgs<RecipeDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelDetail = ViewModelProvider(this)[DetailViewModel::class.java]
        val recipe = args.recipe
        viewModelDetail.saveRecipe(recipe)

        binding.apply {
            viewModel = viewModelDetail
            url = recipe.url
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}