package com.zerodevbuilds.eated

import android.app.Application
import com.zerodevbuilds.eated.data.local.EatedDatabase
import com.zerodevbuilds.eated.data.repository.RestaurantRepository

class EatedApplication : Application() {
    val database by lazy { EatedDatabase.getDatabase(this) }
    val repository by lazy {
        RestaurantRepository(database.restaurantDao(), database.dishDao())
    }
}
