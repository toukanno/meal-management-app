package com.example.mealmanagementapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.mealmanagementapp.data.MealDatabase
import com.example.mealmanagementapp.data.MealRepository
import com.example.mealmanagementapp.ui.MealApp
import com.example.mealmanagementapp.ui.theme.MealManagementTheme
import com.example.mealmanagementapp.viewmodel.MealViewModel
import com.example.mealmanagementapp.viewmodel.MealViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: MealViewModel by viewModels {
        MealViewModelFactory(
            MealRepository(
                MealDatabase.create(applicationContext).mealDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealManagementTheme {
                MealApp(viewModel = viewModel)
            }
        }
    }
}
