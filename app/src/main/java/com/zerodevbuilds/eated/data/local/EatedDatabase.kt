package com.zerodevbuilds.eated.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zerodevbuilds.eated.data.local.dao.DishDao
import com.zerodevbuilds.eated.data.local.dao.RestaurantDao
import com.zerodevbuilds.eated.data.local.entity.DishEntity
import com.zerodevbuilds.eated.data.local.entity.RestaurantEntity
import androidx.room.migration.Migration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [RestaurantEntity::class, DishEntity::class],
    version = 3,
    exportSchema = false
)
abstract class EatedDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun dishDao(): DishDao

    companion object {
        @Volatile
        private var INSTANCE: EatedDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE restaurants ADD COLUMN flair TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Recreate restaurants table with nullable rating
                db.execSQL("CREATE TABLE restaurants_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, rating INTEGER, flair TEXT NOT NULL DEFAULT '')")
                db.execSQL("INSERT INTO restaurants_new (id, name, rating, flair) SELECT id, name, rating, flair FROM restaurants")
                db.execSQL("DROP TABLE restaurants")
                db.execSQL("ALTER TABLE restaurants_new RENAME TO restaurants")

                // Recreate dishes table with nullable rating
                db.execSQL("CREATE TABLE dishes_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, restaurantId INTEGER NOT NULL, name TEXT NOT NULL, rating INTEGER, notes TEXT NOT NULL DEFAULT '', FOREIGN KEY(restaurantId) REFERENCES restaurants(id) ON DELETE CASCADE)")
                db.execSQL("INSERT INTO dishes_new (id, restaurantId, name, rating, notes) SELECT id, restaurantId, name, rating, notes FROM dishes")
                db.execSQL("DROP TABLE dishes")
                db.execSQL("ALTER TABLE dishes_new RENAME TO dishes")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_dishes_restaurantId ON dishes (restaurantId)")
            }
        }

        fun getDatabase(context: Context): EatedDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    EatedDatabase::class.java,
                    "eated_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .addCallback(SeedCallback())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    private class SeedCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    seedDatabase(database.restaurantDao(), database.dishDao())
                }
            }
        }
    }
}

private suspend fun seedDatabase(restaurantDao: RestaurantDao, dishDao: DishDao) {
    // Great restaurant
    val sushiId = restaurantDao.insert(RestaurantEntity(name = "Sushi Palace", rating = 9))
    dishDao.insert(DishEntity(restaurantId = sushiId, name = "Dragon Roll", rating = 9, notes = "Amazing presentation, fresh fish"))
    dishDao.insert(DishEntity(restaurantId = sushiId, name = "Miso Soup", rating = 7, notes = "Warm and comforting"))
    dishDao.insert(DishEntity(restaurantId = sushiId, name = "Salmon Sashimi", rating = 10, notes = "Melt in your mouth"))

    // Decent restaurant
    val burgerId = restaurantDao.insert(RestaurantEntity(name = "Corner Burger", rating = 6))
    dishDao.insert(DishEntity(restaurantId = burgerId, name = "Cheeseburger", rating = 7, notes = "Solid classic burger"))
    dishDao.insert(DishEntity(restaurantId = burgerId, name = "Fries", rating = 5, notes = "A bit soggy"))
    dishDao.insert(DishEntity(restaurantId = burgerId, name = "Milkshake", rating = 8, notes = "Thick and creamy, best part"))

    // Poor restaurant
    val noodleId = restaurantDao.insert(RestaurantEntity(name = "Sad Noodles", rating = 3))
    dishDao.insert(DishEntity(restaurantId = noodleId, name = "Cold Ramen", rating = 2, notes = "Lukewarm broth, overcooked noodles"))
    dishDao.insert(DishEntity(restaurantId = noodleId, name = "Soggy Gyoza", rating = 4, notes = "Not crispy at all"))

    // Another great one
    val tacoId = restaurantDao.insert(RestaurantEntity(name = "Taco Fiesta", rating = 8))
    dishDao.insert(DishEntity(restaurantId = tacoId, name = "Al Pastor Tacos", rating = 9, notes = "Perfectly spiced"))
    dishDao.insert(DishEntity(restaurantId = tacoId, name = "Guacamole", rating = 8))
    dishDao.insert(DishEntity(restaurantId = tacoId, name = "Churros", rating = 7, notes = "Crispy outside, soft inside"))

    // Mid-range
    val pizzaId = restaurantDao.insert(RestaurantEntity(name = "Pizza Corner", rating = 5))
    dishDao.insert(DishEntity(restaurantId = pizzaId, name = "Margherita", rating = 6, notes = "Decent but nothing special"))
    dishDao.insert(DishEntity(restaurantId = pizzaId, name = "Garlic Bread", rating = 5))
}
