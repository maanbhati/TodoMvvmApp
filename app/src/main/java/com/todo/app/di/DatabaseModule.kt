package com.todo.app.di

import android.app.Application
import androidx.room.Room
import com.todo.app.model.roomdb.RecipeDao
import com.todo.app.model.roomdb.RecipeDatabase
import com.todo.app.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
        callback: RecipeDatabase.Callback
    ): RecipeDatabase {
        return Room.databaseBuilder(application, RecipeDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideRecipeDao(db: RecipeDatabase): RecipeDao {
        return db.getRecipeDao()
    }
}