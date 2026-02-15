package com.zerodevbuilds.eated.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zerodevbuilds.eated.data.local.dao.DishDao
import com.zerodevbuilds.eated.data.local.dao.RestaurantDao
import com.zerodevbuilds.eated.data.local.entity.DishEntity
import com.zerodevbuilds.eated.data.local.entity.RestaurantEntity

@Database(
    entities = [RestaurantEntity::class, DishEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EatedDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun dishDao(): DishDao

    companion object {
        @Volatile
        private var INSTANCE: EatedDatabase? = null

        fun getDatabase(context: Context): EatedDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    EatedDatabase::class.java,
                    "eated_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
