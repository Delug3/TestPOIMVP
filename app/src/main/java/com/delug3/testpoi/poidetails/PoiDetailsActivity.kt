package com.delug3.testpoi.poidetails

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.delug3.testpoi.R

class PoiDetailsActivity : AppCompatActivity() {
    private var textViewTitle: TextView? = null
    private var textViewAddress: TextView? = null
    private var textViewTransport: TextView? = null
    private var textViewEmail: TextView? = null
    private var textViewGeocoordinates: TextView? = null
    private var textViewDescription: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pois_details)
        initUI()
        val title: String?
        val address: String?
        val transport: String?
        val email: String?
        val geocoordinates: String?
        val description: String?

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
            } else {
                title = extras.getString("POI_TITLE")
                address = extras.getString("POI_ADDRESS")
                transport = extras.getString("POI_TRANSPORT")
                email = extras.getString("POI_EMAIL")
                geocoordinates = extras.getString("POI_GEOCOORDINATES")
                description = extras.getString("POI_DESCRIPTION")

                textViewTitle!!.text = title
                textViewAddress!!.text = address
                textViewTransport!!.text = transport
                textViewEmail!!.text = email
                textViewGeocoordinates!!.text = geocoordinates
                textViewDescription!!.text = description
            }
        } else {
            savedInstanceState.getSerializable("POI_TITLE") as String?
            savedInstanceState.getSerializable("POI_ADDRESS") as String?
            savedInstanceState.getSerializable("POI_TRANSPORT") as String?
            savedInstanceState.getSerializable("POI_EMAIL") as String?
            savedInstanceState.getSerializable("POI_GEOCOORDINATES") as String?
            savedInstanceState.getSerializable("POI_DESCRIPTION") as String?
        }
    }

    private fun initUI() {
        textViewTitle = findViewById(R.id.text_view_title)
        textViewAddress = findViewById(R.id.text_view_geocoordinates)
        textViewTransport = findViewById(R.id.text_view_transport)
        textViewEmail = findViewById(R.id.text_view_email)
        textViewGeocoordinates = findViewById(R.id.text_view_geocoordinates)
        textViewDescription = findViewById(R.id.text_view_description)
    }
}