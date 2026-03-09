package com.example.mealmanagementapp.domain

import com.example.mealmanagementapp.data.MealEntry

object CalorieSummary {
    fun total(entries: List<MealEntry>): Int = entries.sumOf { it.calories }
}
