package com.delug3.testpoi.poilist

import android.util.Log
import com.delug3.testpoi.database.dao.PoiDao
import com.delug3.testpoi.database.entity.PoiRoom
import com.delug3.testpoi.model.Poi
import com.delug3.testpoi.model.PoiResponse
import com.delug3.testpoi.network.ApiClient.client
import com.delug3.testpoi.network.ApiInterface
import com.delug3.testpoi.poilist.PoiListContract.Model.OnFinishedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PoiListModel : PoiListContract.Model {
        /**
     * getting all data from endpoint asynchronously
     */
    override fun getOnlineData(onFinishedListener: OnFinishedListener?) {

        //calling instance with url(http://t21services.herokuapp.com/)
        val service = client!!.create(ApiInterface::class.java)
        val call = service.pOIs
        call!!.enqueue(object : Callback<PoiResponse?> {
            override fun onResponse(call: Call<PoiResponse?>, response: Response<PoiResponse?>) {
                if (response.isSuccessful) {
                    val poiResponse = response.body()
                    val poiList: ArrayList<Poi>? = poiResponse!!.list

                    //HERE RESPONSE DATA TO LOCAL DATABASE->POIDAO(insertAll)
                    //not working
                    /*val poiListRoom: ArrayList<PoiRoom>?
                    if (poiList != null) {
                        for (i in poiList)

                    }
                    poiDao.insertAllPois(poiList)
                    */


                    onFinishedListener!!.onFinished(poiList)
                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<PoiResponse?>, t: Throwable) {
                Log.e(TAG, "onFailure" + t.message)
                onFinishedListener!!.onFailure(t)
            }
        })
    }

    /**
     * method for obtaining all the details from one object
     * obtained by id, type String
     * @param onFinishedListener: listener to know when the query ends
     * @param idPoi: string where the id of the poi object is stored
     */
    override fun getPoiDetails(onFinishedListener: OnFinishedListener?, idPoi: String?) {
        val service = client!!.create(ApiInterface::class.java)
        val call = service.getPOI(idPoi)
        call!!.enqueue(object : Callback<Poi?> {
            override fun onResponse(call: Call<Poi?>, response: Response<Poi?>) {
                if (response.isSuccessful) {
                    val title = response.body()!!.title
                    val address = response.body()!!.address
                    val transport = response.body()!!.transport
                    val email = response.body()!!.email
                    val geocoordinates = response.body()!!.geocoordinates
                    val description = response.body()!!.description

                    //sending data from respond to a method with an intent
                    //this method will open a new activity with the detailed information
                    onFinishedListener!!.onDetailsFinished(title, address, transport, email, geocoordinates, description)
                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<Poi?>, t: Throwable) {
                Log.e(TAG, "onFailure" + t.message)
            }
        })
    }

    /**
     * Getting data from room database and send it to adapter for being show in recycler
     */
    override fun getOfflineData(onFinishedListener: OnFinishedListener?) {

        //poiDao.getAllPois()

    }

    companion object {
        private const val TAG = "POIs"
    }
}