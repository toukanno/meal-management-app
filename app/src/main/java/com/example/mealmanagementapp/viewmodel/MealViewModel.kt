package com.example.mealmanagementapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mealmanagementapp.data.MealEntry
import com.example.mealmanagementapp.data.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

data class MealUiState(
    val mealType: String = "朝食",
    val mealName: String = "",
    val caloriesText: String = "",
    val note: String = "",
    val meals: List<MealEntry> = emptyList(),
    val totalCalories: Int = 0,
    val errorMessage: String? = null,
)

class MealViewModel(
    private val repository: MealRepository,
) : ViewModel() {
    private val formState = MutableStateFlow(MealUiState())

    val uiState: StateFlow<MealUiState> = combine(
        formState,
        repository.observeMeals(),
        repository.observeTotalCalories(),
    ) { form, meals, totalCalories ->
        form.copy(meals = meals, totalCalories = totalCalories)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MealUiState())

    fun updateMealType(value: String) {
        formState.value = formState.value.copy(mealType = value)
    }

    fun updateMealName(value: String) {
        formState.value = formState.value.copy(mealName = value)
    }

    fun updateCalories(value: String) {
        formState.value = formState.value.copy(caloriesText = value.filter { it.isDigit() })
    }

    fun updateNote(value: String) {
        formState.value = formState.value.copy(note = value)
    }

    fun addMeal() {
        val state = formState.value
        val calories = state.caloriesText.toIntOrNull()

        if (state.mealName.isBlank() || calories == null) {
            formState.value = state.copy(errorMessage = "メニュー名とカロリーを入力してください")
            return
        }

        viewModelScope.launch {
            repository.addMeal(
                mealType = state.mealType,
                mealName = state.mealName.trim(),
                calories = calories,
                note = state.note.trim(),
            )
            formState.value = MealUiState(mealType = state.mealType)
        }
    }

    fun clearError() {
        formState.value = formState.value.copy(errorMessage = null)
    }
}

class MealViewModelFactory(
    private val repository: MealRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
