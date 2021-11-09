package com.todo.retrofitmodule.dto

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("hits")
    val hits: List<Hit>
)

data class Hit(
    @SerializedName("recipe")
    val recipe: Recipe
)

data class Recipe(
    @SerializedName("cuisineType")
    val cuisineType: List<String>,
    @SerializedName("dishType")
    val dishType: List<String>,
    @SerializedName("image")
    val image: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("shareAs")
    val shareAs: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("url")
    val url: String
)
