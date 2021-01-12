package com.example.canis.Places.Information

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.canis.R

class PlacesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)
        supportActionBar?.title = "Places navigation"

        if(savedInstanceState == null ){
            val fragment = PlacesFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.activity_places, fragment).commit()
        }
    }
}
