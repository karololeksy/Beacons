package com.example.canis.WorkersInformationModule

import com.example.canis.RestServiceBuilder
import com.example.canis.WorkersInformationModule.data.RestWorkerProvider
import com.example.canis.WorkersInformationModule.network.WorkersInformationService
import com.example.canis.WorkersInformationModule.network.WorkersListService
import com.example.canis.WorkersInformationModule.model.WorkersService


object InstanceProvider {

    fun getWorkerServiceInstance(): WorkersService {

        val rest1 = RestServiceBuilder.build(WorkersListService::class.java)
        val rest2 = RestServiceBuilder.build(WorkersInformationService::class.java)

        val dataProvider = RestWorkerProvider(rest1,rest2)

        val service = WorkersService(dataProvider)

        return service

    }

}