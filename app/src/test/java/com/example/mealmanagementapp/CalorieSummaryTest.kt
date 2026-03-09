package com.example.mealmanagementapp

import com.example.mealmanagementapp.data.MealEntry
import com.example.mealmanagementapp.domain.CalorieSummary
import org.junit.Assert.assertEquals
import org.junit.Test

class CalorieSummaryTest {
    @Test
    fun total_returnsSumOfCalories() {
        val meals = listOf(
            MealEntry(mealType = "朝食", mealName = "卵", calories = 120, note = ""),
            MealEntry(mealType = "昼食", mealName = "サラダ", calories = 280, note = ""),
            MealEntry(mealType = "夕食", mealName = "魚", calories = 430, note = ""),
        )

        assertEquals(830, CalorieSummary.total(meals))
    }
}
