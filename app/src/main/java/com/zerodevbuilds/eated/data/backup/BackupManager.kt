package com.zerodevbuilds.eated.data.backup

import com.zerodevbuilds.eated.data.local.entity.DishEntity
import com.zerodevbuilds.eated.data.local.entity.RestaurantEntity
import com.zerodevbuilds.eated.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.first

object BackupManager {

    suspend fun exportData(repository: RestaurantRepository): String {
        val allData = repository.getAllRestaurantsWithDishes().first()
        return toJson(allData)
    }

    suspend fun importData(repository: RestaurantRepository, jsonString: String) {
        val backupRestaurants = fromJson(jsonString)

        repository.deleteAllRestaurants()

        for (br in backupRestaurants) {
            val restaurantId = repository.insertRestaurant(
                RestaurantEntity(name = br.name, rating = br.rating)
            )
            for (bd in br.dishes) {
                repository.insertDish(
                    DishEntity(
                        restaurantId = restaurantId,
                        name = bd.name,
                        rating = bd.rating,
                        notes = bd.notes
                    )
                )
            }
        }
    }
}
