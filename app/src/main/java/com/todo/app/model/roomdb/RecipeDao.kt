package com.todo.app.model.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.todo.app.model.domainmodel.RecipeDomainModel
import com.todo.app.utils.TABLE_NAME

@Dao
interface RecipeDao {
    @Query("SELECT * FROM $TABLE_NAME WHERE label LIKE :query ORDER BY label")
    fun getSavedRecipe(query: String): LiveData<List<RecipeDomainModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeDomainModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeDomainModel>)

    @Delete
    suspend fun delete(recipe: RecipeDomainModel)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAllRecipe()
}