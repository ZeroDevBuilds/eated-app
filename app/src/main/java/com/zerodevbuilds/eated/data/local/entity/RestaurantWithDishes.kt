package com.zerodevbuilds.eated.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RestaurantWithDishes(
    @Embedded val restaurant: RestaurantEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "restaurantId"
    )
    val dishes: List<DishEntity>
)
