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

enum class SortOption(val label: String) {
    RECENTLY_MODIFIED("Recent"),
    DATE_ADDED("Date Added"),
    RATING("Rating"),
    ALPHABETICAL("A–Z")
}

class RestaurantListViewModel(
    repository: RestaurantRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val sortOption = MutableStateFlow(SortOption.RECENTLY_MODIFIED)

    private val allRestaurants = repository.getAllRestaurantsWithDishes()

    val restaurants: StateFlow<List<RestaurantWithDishes>> =
        combine(allRestaurants, searchQuery, sortOption) { list, query, sort ->
            val filtered = if (query.isBlank()) list
            else list.filter { rwd ->
                rwd.restaurant.name.contains(query, ignoreCase = true) ||
                    rwd.dishes.any { it.name.contains(query, ignoreCase = true) }
            }
            when (sort) {
                SortOption.RECENTLY_MODIFIED -> filtered.sortedByDescending { it.restaurant.modifiedAt }
                SortOption.DATE_ADDED -> filtered.sortedByDescending { it.restaurant.createdAt }
                SortOption.RATING -> filtered.sortedByDescending { it.restaurant.rating ?: -1 }
                SortOption.ALPHABETICAL -> filtered.sortedBy { it.restaurant.name.lowercase() }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    fun onSortOptionChange(option: SortOption) {
        sortOption.value = option
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
    val rated = restaurantWithDishes.dishes.mapNotNull { it.rating }
    return if (rated.isEmpty()) null else rated.average()
}
