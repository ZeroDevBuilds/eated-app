package com.zerodevbuilds.eated.ui.screen.restaurantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zerodevbuilds.eated.data.local.entity.RestaurantWithDishes
import com.zerodevbuilds.eated.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class RestaurantListViewModel(
    repository: RestaurantRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val allRestaurants = repository.getAllRestaurantsWithDishes()

    val restaurants: StateFlow<List<RestaurantWithDishes>> =
        combine(allRestaurants, searchQuery) { list, query ->
            if (query.isBlank()) list
            else list.filter { rwd ->
                rwd.restaurant.name.contains(query, ignoreCase = true) ||
                    rwd.dishes.any { it.name.contains(query, ignoreCase = true) }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    companion object {
        fun factory(repository: RestaurantRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RestaurantListViewModel(repository) as T
            }
        }
    }
}

fun calculateDishAverage(restaurantWithDishes: RestaurantWithDishes): Double? {
    val dishes = restaurantWithDishes.dishes
    return if (dishes.isEmpty()) null else dishes.map { it.rating }.average()
}
