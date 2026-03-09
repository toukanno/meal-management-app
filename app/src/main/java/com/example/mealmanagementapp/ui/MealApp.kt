package com.example.mealmanagementapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mealmanagementapp.data.MealEntry
import com.example.mealmanagementapp.viewmodel.MealUiState
import com.example.mealmanagementapp.viewmodel.MealViewModel

private val mealTypes = listOf("朝食", "昼食", "夕食", "間食")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealApp(viewModel: MealViewModel) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("食事管理アプリ") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("合計カロリー: ${state.totalCalories} kcal", style = MaterialTheme.typography.titleMedium)
            MealInputForm(state = state, viewModel = viewModel)
            Divider()
            Text("履歴", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            MealList(state.meals)
        }
    }
}

@Composable
private fun MealInputForm(state: MealUiState, viewModel: MealViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("食事登録", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = { expanded = true }) {
                    Text("食事タイプ: ${state.mealType}")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    mealTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                viewModel.updateMealType(type)
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = state.mealName,
                onValueChange = viewModel::updateMealName,
                label = { Text("メニュー名") },
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = state.caloriesText,
                onValueChange = viewModel::updateCalories,
                label = { Text("カロリー") },
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = state.note,
                onValueChange = viewModel::updateNote,
                label = { Text("メモ") },
                modifier = Modifier.fillMaxWidth(),
            )
            Button(onClick = viewModel::addMeal, modifier = Modifier.fillMaxWidth()) {
                Text("保存")
            }
        }
    }
}

@Composable
private fun MealList(meals: List<MealEntry>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(meals, key = { it.id }) { meal ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("${meal.mealType} - ${meal.mealName}", fontWeight = FontWeight.Bold)
                    Text("${meal.calories} kcal")
                    if (meal.note.isNotBlank()) {
                        Text("メモ: ${meal.note}")
                    }
                }
            }
        }
    }
}
