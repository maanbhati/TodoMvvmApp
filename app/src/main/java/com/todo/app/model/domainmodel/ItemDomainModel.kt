package com.todo.app.model.domainmodel

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.todo.app.utils.NEW_LINE
import com.todo.app.utils.TABLE_NAME
import com.todo.retrofitmodule.dto.ItemResponse
import com.todo.retrofitmodule.dto.Recipe
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class ItemDomainModel(
    val hits: List<HitDomainModel>
) {
    constructor(itemResponse: ItemResponse) : this(
        hits = itemResponse.hits.map {
            HitDomainModel(RecipeDomainModel(it.recipe))
        }
    )
}

data class HitDomainModel(
    val recipe: RecipeDomainModel
)

@Parcelize
@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = ["label", "image"], unique = true)]
)
data class RecipeDomainModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cuisineType: @RawValue List<String>,
    val dishType: @RawValue List<String>,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "label")
    val label: String,
    val shareAs: String,
    val source: String,
    val url: String = "",
    val recipeDetail: String = ""
) : Parcelable {
    constructor(recipe: Recipe) : this(
        cuisineType = recipe.cuisineType,
        dishType = recipe.dishType,
        image = recipe.image,
        label = recipe.label,
        shareAs = recipe.shareAs,
        source = recipe.source,
        url = recipe.url,
        recipeDetail = recipe.label.plus(NEW_LINE)
            .plus(recipe.dishType[0]).plus(NEW_LINE)
            .plus(recipe.cuisineType[0]).plus(NEW_LINE)
            .plus(recipe.source).plus(NEW_LINE)
    )
}

