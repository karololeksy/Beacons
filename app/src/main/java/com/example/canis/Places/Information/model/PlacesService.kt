package com.example.canis.Places.Information.model

import com.example.canis.Places.Information.data.PlacesDataProvider

class PlacesService(private val dataProvider: PlacesDataProvider) {

    suspend fun fetchPlaces(): List<Place>{
        return dataProvider.fetchPlaces()
    }

}