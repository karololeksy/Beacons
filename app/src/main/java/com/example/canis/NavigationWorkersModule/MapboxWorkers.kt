package com.example.canis.NavigationWorkersModule

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.view.ActionMode
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.canis.R
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode
// classes needed to add a marker
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Consumer

class MapboxWorkers() : AppCompatActivity(), PermissionsListener, LocationEngineListener, MapboxMap.OnMapClickListener {
    override fun onMapClick(point: LatLng) {
        Log.i("map clicked",":)")
    }

    companion object {
        var active = false
    }

    private lateinit var mapView: MapView
    private lateinit var map: MapboxMap
    private lateinit var permissionManager: PermissionsManager
    private lateinit var originLocation: Location
    private lateinit var startButton: Button
    private lateinit var originPosition: Point
    private lateinit var destinationPosition: Point

    private var locationEngine: LocationEngine? = null
    private var locationLayerPlugin: LocationLayerPlugin? = null
    private var destinationMarker: Marker? = null

    //navigation
    var navigationMapRoute: NavigationMapRoute? = null
    var currentRoute: DirectionsRoute? = null

    //coords for workers
    private var latWorker: Double = 0.0
    private var lonWorker: Double = 0.0
    private var placeWorker: String = ""
    private var isWorker = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Mapbox.getInstance(this, getString(R.string.access_token))
        setContentView(R.layout.activity_mapbox_workers)

        isWorker = intent.extras?.getBoolean("isWorker",false) ?: false
        if(isWorker){
            lonWorker = intent.extras?.getDouble("lonWorker") ?: 0.0
            latWorker = intent.extras?.getDouble("latWorker") ?: 0.0
            placeWorker = intent.extras?.getString("workerPlace") ?: ""
        }

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        //button
        startButton = findViewById(R.id.startButton)

        mapView.getMapAsync { mapboxMap -> //called when out map is ready to go
            map = mapboxMap
            enableLocation()
        }

        startButton.setOnClickListener {
            val navigationLauncherOptions = NavigationLauncherOptions.builder() //1
                    .directionsProfile(DirectionsCriteria.PROFILE_WALKING)
                    .directionsRoute(currentRoute) //2
                    .shouldSimulateRoute(true) //3
                    .build()

            NavigationLauncher.startNavigation(this,navigationLauncherOptions) //4
        }

    }

    fun enableLocation(){
        //check if we granted permission for location
        if(PermissionsManager.areLocationPermissionsGranted(this)){
            initializeLocationEngine()
            initializeLocationLayer()
            map.addOnMapClickListener(this)
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }
    }

    //gives user location
    @SuppressWarnings("MissingPermission")
    private fun initializeLocationEngine(){
        locationEngine = LocationEngineProvider(this).obtainBestLocationEngineAvailable()
        locationEngine?.priority = LocationEnginePriority.HIGH_ACCURACY
        locationEngine?.activate()

        val lastLocation = locationEngine?.lastLocation
        if(lastLocation != null){
            originLocation = lastLocation
            setCameraPosition(lastLocation)
        } else {
            locationEngine?.addLocationEngineListener(this)
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun initializeLocationLayer(){
        locationLayerPlugin = LocationLayerPlugin(mapView, map, locationEngine)
        locationLayerPlugin?.setLocationLayerEnabled(true)
        locationLayerPlugin?.cameraMode = CameraMode.TRACKING
        locationLayerPlugin?.renderMode = RenderMode.NORMAL
    }

    private fun setCameraPosition(location: Location){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 13.0))
    }

    fun setupMap() {

        originPosition = Point.fromLngLat(originLocation.longitude, originLocation.latitude)
        val startPoint = Point.fromLngLat(originLocation.longitude, originLocation.latitude)
        val endPoint: Point?
        if(isWorker){

            Log.i("supertest",lonWorker.toString() + " " + latWorker.toString())

            if(placeWorker.contains("A10"))
                endPoint = Point.fromLngLat(19.4529520,51.7524802)
            else
                endPoint = Point.fromLngLat(lonWorker,latWorker)        } else {
            endPoint = Point.fromLngLat(destinationPosition.longitude(), destinationPosition.latitude())
        }
        startButton.isEnabled = true
        startButton.setBackgroundResource(R.color.mapboxBlue)
        getRoute(startPoint,endPoint)
    }


    //when user declines permission
    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Toast.makeText(this,"Please give me permission.",Toast.LENGTH_LONG).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if(granted){
            enableLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onLocationChanged(location: Location?) {
        location?.let {
            originLocation = location
            setCameraPosition(location)
            setupMap()
            setCameraPosition(location)
        }
    }

    //navigation part
    fun getRoute(originPoint: Point, endPoint: Point) {
        val coorsList = mutableListOf<Point>()
        coorsList.add(originPoint)
        coorsList.add(endPoint)

        NavigationRoute.builder(this) //1
                .accessToken(Mapbox.getAccessToken()!!) //2
                .origin(coorsList[0]) //3
                .destination(coorsList[1]) //4
                .profile("walking")
                .build() //5
                .getRoute(object : Callback<DirectionsResponse> { //6
                    override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                        Log.d("MainActivity", call.toString())
                    }

                    override fun onResponse(call: Call<DirectionsResponse>,
                                            response: Response<DirectionsResponse>
                    ) {
                        if (navigationMapRoute != null) {
                            navigationMapRoute?.updateRouteVisibilityTo(false)
                        } else {
                            navigationMapRoute = NavigationMapRoute(null, mapView, map)
                        }



                        currentRoute = response.body()?.routes()?.first()
                        if (currentRoute != null) {
                            navigationMapRoute?.addRoute(currentRoute)
                        }
                    }
                })
    }

    @SuppressLint("MissingPermission")
    private fun checkLocation() {
        if (originLocation == null) {
            map.locationComponent.lastKnownLocation?.run {
                originLocation = this
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    override fun onConnected() {
        locationEngine?.requestLocationUpdates()
    }

    @SuppressWarnings("MissingPermission")
    override fun onStart() {
        super.onStart()
        active = true
        if(PermissionsManager.areLocationPermissionsGranted(this)){
            locationEngine?.requestLocationUpdates()
            locationLayerPlugin?.onStart()
        }
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        active = false
        locationEngine?.removeLocationUpdates()
        locationLayerPlugin?.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        locationEngine?.deactivate()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if(outState != null){
            mapView.onSaveInstanceState(outState)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}