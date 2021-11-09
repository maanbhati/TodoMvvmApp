package com.todo.app.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.todo.app.databinding.ActivityRecipeSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityRecipeSearchBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }
}