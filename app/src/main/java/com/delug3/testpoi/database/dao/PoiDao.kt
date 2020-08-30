package com.delug3.testpoi.database.dao



import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.delug3.testpoi.database.entity.PoiRoom
import java.util.ArrayList

@Dao
interface PoiDao {

    @Insert
    fun insert(poiRoom: PoiRoom)

   @Insert(onConflict = REPLACE)
   fun insertAllPois(poiRoom: ArrayList<PoiRoom>?);


   @Query("SELECT * FROM poi_table")
   fun getAllPois(): LiveData<List<PoiRoom>>



}