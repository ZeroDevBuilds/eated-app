package com.zerodevbuilds.eated.ui.screen.restaurantform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zerodevbuilds.eated.data.local.entity.RestaurantEntity
import com.zerodevbuilds.eated.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RestaurantFormState(
    val name: String = "",
    val flair: String = "",
    val rating: Int? = null,
    val isEdit: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false
)

class RestaurantFormViewModel(
    private val repository: RestaurantRepository,
    private val restaurantId: Long?
) : ViewModel() {

    private val _state = MutableStateFlow(RestaurantFormState())
    val state: StateFlow<RestaurantFormState> = _state

    init {
        if (restaurantId != null) {
            _state.value = _state.value.copy(isEdit = true, isLoading = true)
            viewModelScope.launch {
                val restaurant = repository.getRestaurantById(restaurantId)
                if (restaurant != null) {
                    _state.value = _state.value.copy(
                        name = restaurant.name,
                        flair = restaurant.flair,
                        rating = restaurant.rating,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onFlairChange(flair: String) {
        _state.value = _state.value.copy(flair = flair)
    }

    fun onRatingChange(rating: Int?) {
        _state.value = _state.value.copy(rating = rating)
    }

    fun save() {
        val s = _state.value
        if (s.name.isBlank()) return
        viewModelScope.launch {
            if (restaurantId != null) {
                repository.updateRestaurant(
                    RestaurantEntity(id = restaurantId, name = s.name.trim(), flair = s.flair.trim(), rating = s.rating)
                )
            } else {
                repository.insertRestaurant(
                    RestaurantEntity(name = s.name.trim(), flair = s.flair.trim(), rating = s.rating)
                )
            }
            _state.value = _state.value.copy(isSaved = true)
        }
    }

    fun delete() {
        if (restaurantId == null) return
        viewModelScope.launch {
            repository.deleteRestaurant(restaurantId)
            _state.value = _state.value.copy(isDeleted = true)
        }
    }

    companion object {
        fun factory(repository: RestaurantRepository, restaurantId: Long?) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RestaurantFormViewModel(repository, restaurantId) as T
                }
            }
    }
}
