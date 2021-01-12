package com.example.canis.BeaconModule.BeaconInformationModule.network;

import com.example.canis.BeaconModule.BeaconInformationModule.model.BuildingDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BeaconInformationController {

    @GET("/api/building-info/{id}")
    Call<BuildingDetail> getBuilding(@Path("id") String id);
}
