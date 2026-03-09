package com.example.mealmanagementapp.data

import kotlinx.coroutines.flow.Flow

class MealRepository(
    private val mealDao: MealDao,
) {
    fun observeMeals(): Flow<List<MealEntry>> = mealDao.observeAll()

    fun observeTotalCalories(): Flow<Int> = mealDao.observeTotalCalories()

    suspend fun addMeal(mealType: String, mealName: String, calories: Int, note: String) {
        mealDao.insert(
            MealEntry(
                mealType = mealType,
                mealName = mealName,
                calories = calories,
                note = note,
            )
        )
    }
}
