package com.example.mealmanagementapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealEntry::class], version = 1, exportSchema = false)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        fun create(context: Context): MealDatabase {
            return Room.databaseBuilder(
                context,
                MealDatabase::class.java,
                "meal_database",
            ).fallbackToDestructiveMigration().build()
        }
    }
}
