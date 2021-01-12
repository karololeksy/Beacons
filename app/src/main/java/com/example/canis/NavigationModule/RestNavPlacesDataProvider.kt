package com.example.canis.NavigationModule

class RestNavPlacesDataProvider(private val navPlacesDataService: NavPlacesService): NavPlacesDataProvider {

    override suspend fun fetchListOfPlaces(): List<NavPlace> {
        return navPlacesDataService.workerInformation()
    }

}