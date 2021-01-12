package com.example.canis.NavigationModule

interface NavPlacesDataProvider {
    suspend fun fetchListOfPlaces(): List<NavPlace>
}