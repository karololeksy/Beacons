package com.example.canis.Places.Information.data

import com.example.canis.Places.Information.model.Place
import com.example.canis.Places.Information.network.PlacesListService

class RestPlacesProvider(private val placesListService: PlacesListService): PlacesDataProvider{
    override suspend fun fetchPlaces(): List<Place> {
        return placesListService.listOfWorkers()
    }
}