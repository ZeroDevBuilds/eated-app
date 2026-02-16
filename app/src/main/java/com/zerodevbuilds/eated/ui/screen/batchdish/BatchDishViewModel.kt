package com.zerodevbuilds.eated.ui.screen.batchdish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zerodevbuilds.eated.data.local.entity.DishEntity
import com.zerodevbuilds.eated.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class BatchDishEntry(
    val name: String = "",
    val rating: Int? = null,
    val notes: String = ""
)

data class BatchDishState(
    val entries: List<BatchDishEntry> = listOf(BatchDishEntry()),
    val isSaved: Boolean = false
)

class BatchDishViewModel(
    private val repository: RestaurantRepository,
    private val restaurantId: Long
) : ViewModel() {

    private val _state = MutableStateFlow(BatchDishState())
    val state: StateFlow<BatchDishState> = _state

    fun onNameChange(index: Int, name: String) {
        _state.value = _state.value.copy(
            entries = _state.value.entries.toMutableList().also {
                it[index] = it[index].copy(name = name)
            }
        )
    }

    fun onRatingChange(index: Int, rating: Int?) {
        _state.value = _state.value.copy(
            entries = _state.value.entries.toMutableList().also {
                it[index] = it[index].copy(rating = rating)
            }
        )
    }

    fun onNotesChange(index: Int, notes: String) {
        _state.value = _state.value.copy(
            entries = _state.value.entries.toMutableList().also {
                it[index] = it[index].copy(notes = notes)
            }
        )
    }

    fun addEntry() {
        _state.value = _state.value.copy(
            entries = _state.value.entries + BatchDishEntry()
        )
    }

    fun removeEntry(index: Int) {
        if (_state.value.entries.size <= 1) return
        _state.value = _state.value.copy(
            entries = _state.value.entries.toMutableList().also { it.removeAt(index) }
        )
    }

    fun saveAll() {
        val valid = _state.value.entries.filter { it.name.isNotBlank() }
        if (valid.isEmpty()) return
        viewModelScope.launch {
            valid.forEach { entry ->
                repository.insertDish(
                    DishEntity(
                        restaurantId = restaurantId,
                        name = entry.name.trim(),
                        rating = entry.rating,
                        notes = entry.notes.trim()
                    )
                )
            }
            _state.value = _state.value.copy(isSaved = true)
        }
    }

    val hasValidEntries: Boolean
        get() = _state.value.entries.any { it.name.isNotBlank() }

    companion object {
        fun factory(repository: RestaurantRepository, restaurantId: Long) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BatchDishViewModel(repository, restaurantId) as T
                }
            }
    }
}
