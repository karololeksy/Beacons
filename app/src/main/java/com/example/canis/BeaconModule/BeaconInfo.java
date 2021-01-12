package com.example.canis.BeaconModule;

import java.util.Observable;

public final class BeaconInfo extends Observable {

    private static BeaconInfo beaconInfo;

    public static BeaconInfo getInstance() {
        if (beaconInfo == null) {
            beaconInfo = new BeaconInfo();
        }
        return beaconInfo;
    }

    private String beaconId = "";

    private BeaconInfo(){}

    void setNearestBeaconId(String beaconId) {
        this.beaconId = beaconId;
        setChanged();
        notifyObservers(beaconId);
    }

    public String getNearestBeaconId() {
        return beaconId;
    }
}
