package com.delug3.testpoi.database.dao



import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.delug3.testpoi.database.entity.PoiRoom

@Dao
interface PoiDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   fun insert(poiRoom: PoiRoom)

   @Insert(onConflict = REPLACE)
   fun insertAllPois(poisRoomList: List<PoiRoom?>?);

   @Query("SELECT * FROM poi_table")
   fun getAllPois(): LiveData<List<PoiRoom>>

}