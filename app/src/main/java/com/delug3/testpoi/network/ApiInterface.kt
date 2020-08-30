package com.delug3.testpoi.network

import com.delug3.testpoi.model.Poi
import com.delug3.testpoi.model.PoiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    //with this endpoint I can obtain all the POIs
    @get:GET("points")
    val pOIs: Call<PoiResponse?>?

    //this get require an id for obtaining one single object with the detail info
    @GET("points/{id}")
    fun getPOI(@Path(value = "id", encoded = true) id: String?): Call<Poi?>?
}