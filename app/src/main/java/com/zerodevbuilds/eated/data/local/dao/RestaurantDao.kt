package com.zerodevbuilds.eated.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zerodevbuilds.eated.data.local.entity.RestaurantEntity
import com.zerodevbuilds.eated.data.local.entity.RestaurantWithDishes
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Transaction
    @Query("SELECT * FROM restaurants ORDER BY rating DESC")
    fun getAllWithDishes(): Flow<List<RestaurantWithDishes>>

    @Query("SELECT * FROM restaurants WHERE id = :id")
    suspend fun getById(id: Long): RestaurantEntity?

    @Insert
    suspend fun insert(restaurant: RestaurantEntity): Long

    @Update
    suspend fun update(restaurant: RestaurantEntity)

    @Query("DELETE FROM restaurants WHERE id = :id")
    suspend fun deleteById(id: Long)
}
