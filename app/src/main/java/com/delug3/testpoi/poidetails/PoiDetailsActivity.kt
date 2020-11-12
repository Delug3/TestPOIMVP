package com.delug3.testpoi.poidetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.delug3.testpoi.databinding.ActivityPoisDetailsBinding

class PoiDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPoisDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoisDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                binding.textViewTitle.text = title
                binding.textViewAddress.text = address
                binding.textViewTransport.text = transport
                binding.textViewEmail.text = email
                binding.textViewGeocoordinates.text = geocoordinates
                binding.textViewDescription.text = description
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

}