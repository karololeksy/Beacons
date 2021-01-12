package com.example.canis.BeaconModule.BeaconNavigationModule.service;

import com.example.canis.BeaconModule.BeaconNavigationModule.model.Navigator;
import com.example.canis.BeaconModule.BeaconNavigationModule.network.BeaconNavigationController;

import retrofit2.Call;

public class BeaconNavigationService {

    private BeaconNavigationController beaconNavigationController;

    public BeaconNavigationService(BeaconNavigationController beaconNavigationController) {
        this.beaconNavigationController = beaconNavigationController;
    }

    public Call<Navigator> findRoute(String beaconId, String room) {
        return beaconNavigationController.getMap(beaconId,room);
    }
}
