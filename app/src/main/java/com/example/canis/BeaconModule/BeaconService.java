package com.example.canis.BeaconModule;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.example.canis.BeaconModule.BeaconNavigationModule.BeaconNavigationActivity;
import com.example.canis.NavigationModule.MapboxActivity;
import com.example.canis.NavigationWorkersModule.MapboxWorkers;
import com.kontakt.sdk.android.ble.configuration.ScanMode;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.util.List;

public class BeaconService extends Service {

    private static final String API_KEY = "TTMLKMtxDZnZTDymSmRdSgKOjFqBQmRB";

    private final Handler handler = new Handler();
    private ProximityManager proximityManager;
    private boolean isRunning;
    private BeaconInfo beaconInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        KontaktSDK.initialize(API_KEY);
        setupProximityManager();
        beaconInfo = BeaconInfo.getInstance();
        isRunning = false;
    }

    private void setupProximityManager() {
        proximityManager = ProximityManagerFactory.create(this);
        proximityManager.configuration()
                .scanPeriod(ScanPeriod.RANGING)
                .scanMode(ScanMode.BALANCED);
        proximityManager.setIBeaconListener(createIBeaconListener());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isRunning) {
            Toast.makeText(this, "Service is already running.", Toast.LENGTH_SHORT).show();
            return START_STICKY;
        }
        startScanning();
        isRunning = true;
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
                Toast.makeText(BeaconService.this, "Scanning service started.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconsUpdated(List<IBeaconDevice> iBeacons, IBeaconRegion region) {
                if (iBeacons.isEmpty()) {
                    beaconInfo.setNearestBeaconId("");
                    return;
                }
                IBeaconDevice iBeacon = iBeacons.get(0);
                for (IBeaconDevice b : iBeacons) {
                    if (b.getDistance() < iBeacon.getDistance()) {
                        iBeacon = b;
                    }
                }
                if (!beaconInfo.getNearestBeaconId().equals(iBeacon.getUniqueId())) {
                    beaconInfo.setNearestBeaconId(iBeacon.getUniqueId());
                    if(Repeater.getBeacons().contains(iBeacon.getUniqueId()))
                    startActivityIfMapInRunning();
                }
               // Toast.makeText(BeaconService.this, beaconInfo.getNearestBeaconId(), Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void startActivityIfMapInRunning() {
        if(MapboxActivity.Companion.getActive() || MapboxWorkers.Companion.getActive()) {
            startActivity(new Intent(BeaconService.this, BeaconNavigationActivity.class));
        }
    }


    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        if (proximityManager != null) {
            proximityManager.disconnect();
            proximityManager = null;
        }
        Toast.makeText(BeaconService.this, "Scanning service stopped.", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
