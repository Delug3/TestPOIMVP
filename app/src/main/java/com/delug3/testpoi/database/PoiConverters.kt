package com.delug3.testpoi.database

import androidx.room.TypeConverter
import com.delug3.testpoi.model.Poi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class PoiConverters {

    private val gson = Gson()

    @TypeConverter
    fun stringToListPoi(data: String?): List<Poi?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
                TypeToken<List<Poi?>?>() {}.type

        return gson.fromJson<List<Poi?>>(data, listType)
    }

    @TypeConverter
    fun listPoiToString(list: List<Poi?>?): String? {
        return gson.toJson(list)
    }


}



