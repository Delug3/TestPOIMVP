package com.delug3.testpoi.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.delug3.testpoi.database.dao.PoiDao
import com.delug3.testpoi.database.entity.PoiRoom

@Database(entities = [PoiRoom::class], version = 1)
abstract class PoiDatabase : RoomDatabase(){
    abstract fun poiDao(): PoiDao

    companion object {
        private var instance: PoiDatabase? = null
        fun getInstance( context: Context): PoiDatabase? {
            if (instance == null) {
                synchronized(PoiDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext, PoiDatabase::class.java, "pois_database")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return instance
        }
    }
}


