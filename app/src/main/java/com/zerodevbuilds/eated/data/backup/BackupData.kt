package com.zerodevbuilds.eated.data.backup

import com.zerodevbuilds.eated.data.local.entity.RestaurantWithDishes
import org.json.JSONArray
import org.json.JSONObject
import java.time.Instant

data class BackupData(
    val version: Int,
    val exportedAt: String,
    val restaurants: List<BackupRestaurant>
)

data class BackupRestaurant(
    val name: String,
    val rating: Int,
    val flair: String = "",
    val dishes: List<BackupDish>
)

data class BackupDish(
    val name: String,
    val rating: Int,
    val notes: String
)

fun toJson(data: List<RestaurantWithDishes>): String {
    val root = JSONObject()
    root.put("version", 1)
    root.put("exportedAt", Instant.now().toString())

    val restaurantsArray = JSONArray()
    for (rwd in data) {
        val rObj = JSONObject()
        rObj.put("name", rwd.restaurant.name)
        rObj.put("rating", rwd.restaurant.rating)
        rObj.put("flair", rwd.restaurant.flair)

        val dishesArray = JSONArray()
        for (dish in rwd.dishes) {
            val dObj = JSONObject()
            dObj.put("name", dish.name)
            dObj.put("rating", dish.rating)
            dObj.put("notes", dish.notes)
            dishesArray.put(dObj)
        }
        rObj.put("dishes", dishesArray)
        restaurantsArray.put(rObj)
    }
    root.put("restaurants", restaurantsArray)

    return root.toString(2)
}

fun fromJson(jsonString: String): List<BackupRestaurant> {
    val root = JSONObject(jsonString)
    val restaurantsArray = root.optJSONArray("restaurants") ?: return emptyList()

    val result = mutableListOf<BackupRestaurant>()
    for (i in 0 until restaurantsArray.length()) {
        val rObj = restaurantsArray.getJSONObject(i)

        val dishes = mutableListOf<BackupDish>()
        val dishesArray = rObj.optJSONArray("dishes") ?: JSONArray()
        for (j in 0 until dishesArray.length()) {
            val dObj = dishesArray.getJSONObject(j)
            dishes.add(
                BackupDish(
                    name = dObj.optString("name", ""),
                    rating = dObj.optInt("rating", 5),
                    notes = dObj.optString("notes", "")
                )
            )
        }

        result.add(
            BackupRestaurant(
                name = rObj.optString("name", ""),
                rating = rObj.optInt("rating", 5),
                flair = rObj.optString("flair", ""),
                dishes = dishes
            )
        )
    }
    return result
}
