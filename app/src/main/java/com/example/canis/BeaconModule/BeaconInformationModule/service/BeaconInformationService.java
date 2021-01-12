package com.example.canis.BeaconModule.BeaconInformationModule.service;

import com.example.canis.BeaconModule.BeaconInformationModule.model.BuildingDetail;
import com.example.canis.BeaconModule.BeaconInformationModule.network.BeaconInformationController;

import retrofit2.Call;

public class BeaconInformationService {

    private BeaconInformationController beaconInformationController;

    public BeaconInformationService(BeaconInformationController beaconInformationController) {
        this.beaconInformationController = beaconInformationController;
    }

    public Call<BuildingDetail> findBuildingInformation(String id) {
        return beaconInformationController.getBuilding(id);
    }
}
