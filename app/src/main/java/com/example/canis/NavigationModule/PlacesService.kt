package com.example.canis.NavigationModule

class PlacesService(private val dataProvider: NavPlacesDataProvider) {

    suspend fun fetchPlaces(): List<NavPlace>{
        return dataProvider.fetchListOfPlaces()
    }

}