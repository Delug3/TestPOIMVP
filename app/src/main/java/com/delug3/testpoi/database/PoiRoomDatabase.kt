package com.delug3.testpoi.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.delug3.testpoi.database.dao.PoiDao
import com.delug3.testpoi.database.entity.PoiRoom
import kotlinx.coroutines.CoroutineScope

@Database(entities = [PoiRoom::class], version = 1)
@TypeConverters(PoiConverters::class)
abstract class PoiRoomDatabase : RoomDatabase() {

    abstract fun poiDao(): PoiDao

    companion object {
        @Volatile
        private var INSTANCE: PoiRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): PoiRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PoiRoomDatabase::class.java,
                        "poi_database"
                )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                instance
            }
        }

    }
}



