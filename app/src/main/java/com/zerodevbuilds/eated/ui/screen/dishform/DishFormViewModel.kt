package com.zerodevbuilds.eated.ui.screen.dishform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zerodevbuilds.eated.data.local.entity.DishEntity
import com.zerodevbuilds.eated.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DishFormState(
    val name: String = "",
    val rating: Int = 5,
    val notes: String = "",
    val isEdit: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false
)

class DishFormViewModel(
    private val repository: RestaurantRepository,
    private val restaurantId: Long?,
    private val dishId: Long?
) : ViewModel() {

    private val _state = MutableStateFlow(DishFormState())
    val state: StateFlow<DishFormState> = _state

    private var loadedRestaurantId: Long = restaurantId ?: 0

    init {
        if (dishId != null) {
            _state.value = _state.value.copy(isEdit = true, isLoading = true)
            viewModelScope.launch {
                val dish = repository.getDishById(dishId)
                if (dish != null) {
                    loadedRestaurantId = dish.restaurantId
                    _state.value = _state.value.copy(
                        name = dish.name,
                        rating = dish.rating,
                        notes = dish.notes,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onRatingChange(rating: Int) {
        _state.value = _state.value.copy(rating = rating)
    }

    fun onNotesChange(notes: String) {
        _state.value = _state.value.copy(notes = notes)
    }

    fun save() {
        val s = _state.value
        if (s.name.isBlank()) return
        viewModelScope.launch {
            if (dishId != null) {
                repository.updateDish(
                    DishEntity(
                        id = dishId,
                        restaurantId = loadedRestaurantId,
                        name = s.name.trim(),
                        rating = s.rating,
                        notes = s.notes.trim()
                    )
                )
            } else {
                repository.insertDish(
                    DishEntity(
                        restaurantId = loadedRestaurantId,
                        name = s.name.trim(),
                        rating = s.rating,
                        notes = s.notes.trim()
                    )
                )
            }
            _state.value = _state.value.copy(isSaved = true)
        }
    }

    fun delete() {
        if (dishId == null) return
        viewModelScope.launch {
            repository.deleteDish(dishId)
            _state.value = _state.value.copy(isDeleted = true)
        }
    }

    companion object {
        fun factory(repository: RestaurantRepository, restaurantId: Long?, dishId: Long?) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DishFormViewModel(repository, restaurantId, dishId) as T
                }
            }
    }
}
