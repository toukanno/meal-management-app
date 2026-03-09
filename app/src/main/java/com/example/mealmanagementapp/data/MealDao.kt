package com.example.mealmanagementapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: MealEntry)

    @Query("SELECT * FROM meal_entries ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<MealEntry>>

    @Query("SELECT COALESCE(SUM(calories), 0) FROM meal_entries")
    fun observeTotalCalories(): Flow<Int>
}
