package com.example.canis.BeaconModule;

import com.example.canis.BeaconModule.BeaconInformationModule.network.BeaconInformationController;
import com.example.canis.BeaconModule.BeaconInformationModule.service.BeaconInformationService;
import com.example.canis.BeaconModule.BeaconNavigationModule.network.BeaconNavigationController;
import com.example.canis.BeaconModule.BeaconNavigationModule.service.BeaconNavigationService;
import com.example.canis.RestServiceBuilder2;

public class InstanceProvider {

    public static BeaconNavigationService getBuildingMapService() {
        return new BeaconNavigationService(RestServiceBuilder2.INSTANCE.build(BeaconNavigationController.class));
    }

    public static BeaconInformationService getBuildingInfoService() {
        return new BeaconInformationService(RestServiceBuilder2.INSTANCE.build(BeaconInformationController.class));
    }
}
