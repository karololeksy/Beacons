package com.example.canis.Places.Information.data

import com.example.canis.Places.Information.model.Place

interface PlacesDataProvider {

    suspend fun fetchPlaces() : List<Place>

}