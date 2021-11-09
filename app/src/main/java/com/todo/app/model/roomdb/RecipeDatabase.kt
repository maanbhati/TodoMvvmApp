package com.todo.app.model.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.todo.app.model.domainmodel.RecipeDomainModel
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [RecipeDomainModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao

    class Callback @Inject constructor(
        private val database: Provider<RecipeDatabase>
    ) : RoomDatabase.Callback()
}