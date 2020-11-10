package com.delug3.testpoi.poilist

import android.util.Log
import com.delug3.testpoi.database.entity.PoiRoom
import com.delug3.testpoi.model.Poi
import com.delug3.testpoi.model.PoiResponse
import com.delug3.testpoi.network.ApiClient.client
import com.delug3.testpoi.network.ApiInterface
import com.delug3.testpoi.poilist.PoiListContract.Model.OnFinishedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PoiListModel() : PoiListContract.Model {
    /**
     * getting all data from endpoint asynchronously
     */
    override fun getOnlineData(onFinishedListener: OnFinishedListener?) {

        val service = client?.create(ApiInterface::class.java)
        val call = service?.pois
        call!!.enqueue(object : Callback<PoiResponse?> {
            override fun onResponse(call: Call<PoiResponse?>, response: Response<PoiResponse?>) {
                if (response.isSuccessful) {

                    val result = response.body()
                    //here i send the response to a poi model to show it in a recyclerview, works fine
                    val poiList = result?.list

                    //mapping poilist so I can use it in the room database
                    //same list and variables but different type Poi-PoiRoom
                    val mappedRoomList = poiList?.map { PoiRoom(it.id, it.title, it.address, it.transport, it.email, it.geocoordinates, it.description) }

                    onFinishedListener!!.onFinished(poiList, mappedRoomList)

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
    override fun getPoiDetails(onFinishedListener: OnFinishedListener?, idPoi: String) {
        val service = client?.create(ApiInterface::class.java)
        val call = service?.getPoi(idPoi)
        call!!.enqueue(object : Callback<Poi?> {
            override fun onResponse(call: Call<Poi?>, response: Response<Poi?>) {
                if (response.isSuccessful) {
                    val title = response.body()?.title
                    val address = response.body()?.address
                    val transport = response.body()?.transport
                    val email = response.body()?.email
                    val geocoordinates = response.body()?.geocoordinates
                    val description = response.body()?.description

                    //sending data from respond to a method with an intent
                    //this method will open a new activity with the detailed information
                    onFinishedListener!!.onDetailsFinished(idPoi, title, address, transport, email, geocoordinates, description)
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


    }


    companion object {
        private const val TAG = "POIs"
    }
}