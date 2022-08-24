package xyz.snowker.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import xyz.snowker.map.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        val textView = findViewById<TextView>(R.id.text)

        // Instantiate the RequestQueue.
                val queue = Volley.newRequestQueue(this)
                val url = "https://snowker.xyz/test"

        // Request a string response from the provided URL.
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        // Display the first 500 characters of the response string.
                        textView.text = response
                        val coords = response.split(",").toTypedArray()
                        mMap = googleMap

                        // Add a marker in Sydney and move the camera
                        val sydney = LatLng(coords[0].toDouble(), coords[1].toDouble())
                        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in TEC CEM"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    },
                    { textView.text = "That didn't work!" })

        // Add the request to the RequestQueue.
                queue.add(stringRequest)
    }
}