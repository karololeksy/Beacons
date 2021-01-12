package com.example.canis.WorkersInformationModule.model

import com.example.canis.WorkersInformationModule.data.WorkersDataProvider

class WorkersService(private val dataProvider: WorkersDataProvider) {
    suspend fun fetchWorkers(): List<Worker> {
        val listOfWorkers = dataProvider.fetchListOfWorkers()
        return listOfWorkers
    }
}