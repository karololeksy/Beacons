package com.example.canis.WorkersInformationModule.data

import com.example.canis.WorkersInformationModule.model.Worker

interface WorkersDataProvider {

    suspend fun fetchListOfWorkers(): List<Worker>

}