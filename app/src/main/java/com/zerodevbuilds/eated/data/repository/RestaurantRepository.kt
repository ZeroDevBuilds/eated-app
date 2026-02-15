package com.zerodevbuilds.eated.data.repository

import com.zerodevbuilds.eated.data.local.dao.DishDao
import com.zerodevbuilds.eated.data.local.dao.RestaurantDao
import com.zerodevbuilds.eated.data.local.entity.DishEntity
import com.zerodevbuilds.eated.data.local.entity.RestaurantEntity
import com.zerodevbuilds.eated.data.local.entity.RestaurantWithDishes
import kotlinx.coroutines.flow.Flow

class RestaurantRepository(
    private val restaurantDao: RestaurantDao,
    private val dishDao: DishDao
) {
    fun getAllRestaurantsWithDishes(): Flow<List<RestaurantWithDishes>> =
        restaurantDao.getAllWithDishes()

    suspend fun getRestaurantById(id: Long): RestaurantEntity? =
        restaurantDao.getById(id)

    suspend fun insertRestaurant(restaurant: RestaurantEntity): Long =
        restaurantDao.insert(restaurant)

    suspend fun updateRestaurant(restaurant: RestaurantEntity) =
        restaurantDao.update(restaurant)

    suspend fun deleteRestaurant(id: Long) =
        restaurantDao.deleteById(id)

    suspend fun getDishById(id: Long): DishEntity? =
        dishDao.getById(id)

    suspend fun insertDish(dish: DishEntity): Long =
        dishDao.insert(dish)

    suspend fun updateDish(dish: DishEntity) =
        dishDao.update(dish)

    suspend fun deleteDish(id: Long) =
        dishDao.deleteById(id)
}
