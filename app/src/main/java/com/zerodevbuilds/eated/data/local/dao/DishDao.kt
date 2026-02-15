package com.zerodevbuilds.eated.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zerodevbuilds.eated.data.local.entity.DishEntity

@Dao
interface DishDao {
    @Query("SELECT * FROM dishes WHERE id = :id")
    suspend fun getById(id: Long): DishEntity?

    @Insert
    suspend fun insert(dish: DishEntity): Long

    @Update
    suspend fun update(dish: DishEntity)

    @Query("DELETE FROM dishes WHERE id = :id")
    suspend fun deleteById(id: Long)
}
