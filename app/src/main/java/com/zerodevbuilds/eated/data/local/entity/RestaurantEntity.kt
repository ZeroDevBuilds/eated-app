package com.zerodevbuilds.eated.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val rating: Int? = null,
    val flair: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long = System.currentTimeMillis()
)
