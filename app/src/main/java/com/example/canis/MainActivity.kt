package com.example.canis

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.canis.BeaconModule.BeaconInformationModule.BeaconInformationActivity
import com.example.canis.BeaconModule.BeaconNavigationModule.BeaconNavigationActivity
import com.example.canis.BeaconModule.BeaconService
import com.example.canis.Places.Information.PlacesActivity
import com.example.canis.WorkersInformationModule.WorkersActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var beaconService: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beaconService = Intent(applicationContext, BeaconService::class.java)

        startService(beaconService)

        workersButton.setOnClickListener {
            startActivity(Intent(this, WorkersActivity::class.java))
        }

        navigationButton.setOnClickListener{
            //startActivity(Intent(this, MapboxActivity::class.java))
            startActivity(Intent(this, PlacesActivity::class.java))
        }

        beaconInformationButton.setOnClickListener{
            startActivity(Intent(this, BeaconInformationActivity::class.java))
        }

        beaconNavigationButton.setOnClickListener{
            startActivity(Intent(this, BeaconNavigationActivity::class.java))
        }

    }

    override fun onDestroy() {
        stopService(beaconService);
        super.onDestroy()
    }
}