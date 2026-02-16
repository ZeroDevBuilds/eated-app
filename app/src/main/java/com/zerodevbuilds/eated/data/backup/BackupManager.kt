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
            val now = System.currentTimeMillis()
            val restaurantId = repository.insertRestaurant(
                RestaurantEntity(
                    name = br.name,
                    rating = br.rating,
                    flair = br.flair,
                    createdAt = if (br.createdAt > 0) br.createdAt else now,
                    modifiedAt = if (br.modifiedAt > 0) br.modifiedAt else now
                )
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
