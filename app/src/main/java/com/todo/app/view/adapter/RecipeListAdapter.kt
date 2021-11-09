package com.todo.app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todo.app.databinding.ItemListBinding
import com.todo.app.model.domainmodel.RecipeDomainModel
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class RecipeListAdapter @Inject constructor(private val listener: OnItemClickListener) :
    ListAdapter<RecipeDomainModel, RecipeListAdapter.RecipeListViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val binding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListViewHolder(binding)
    }

    override fun onBindViewHolder(holderRecipe: RecipeListViewHolder, position: Int) {
        val currentItem = getItem(position)
        holderRecipe.bind(currentItem)
    }

    inner class RecipeListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position))
                    }
                }
            }
        }

        fun bind(recipe: RecipeDomainModel) {
            binding.apply {
                recipeItem = recipe
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(recipe: RecipeDomainModel)
    }

    class DiffCallback : DiffUtil.ItemCallback<RecipeDomainModel>() {
        override fun areItemsTheSame(
            oldItem: RecipeDomainModel,
            newItem: RecipeDomainModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecipeDomainModel,
            newItem: RecipeDomainModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}