package com.example.canis.BeaconModule.BeaconNavigationModule.network;

import com.example.canis.BeaconModule.BeaconNavigationModule.model.Navigator;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BeaconNavigationController {

    @POST("/api/route/{beacon}/{room}")
    Call<Navigator> getMap(@Path("beacon") String beacon, @Path("room") String room);
}
