package com.example.mealmanagementapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_entries")
data class MealEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mealType: String,
    val mealName: String,
    val calories: Int,
    val note: String,
    val createdAt: Long = System.currentTimeMillis(),
)
