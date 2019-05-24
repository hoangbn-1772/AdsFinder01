package com.sun.adsfinder01.ui.placedetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import com.sun.adsfinder01.R
import com.sun.adsfinder01.util.ContextExtension.showMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class DirectionPlaceActivity : AppCompatActivity(), PermissionsListener, MapboxMap.OnMapClickListener,
    OnMapReadyCallback {

    private lateinit var destinationPoint: LatLng
    private var mapView: MapView? = null
    private var mapboxMap: MapboxMap? = null
    private var permissionManager: PermissionsManager? = null
    private var locationComponent: LocationComponent? = null
    private var navigationMapRoute: NavigationMapRoute? = null
    private var locationEngine: LocationEngine? = null
    private val callback = ActivityLocationCallback(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, resources.getString(R.string.access_token))
        setContentView(R.layout.activity_direction_place)

        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        destinationPoint = intent.extras?.let {
            LatLng(
                it.getDouble(BUNDLE_LAT),
                it.getDouble(BUNDLE_LNG)
            )
        } ?: LatLng(0.0, 0.0)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        this.mapboxMap?.addOnMapClickListener(this)
        this.mapboxMap?.setStyle(Style.MAPBOX_STREETS) { style ->
            enableLocationComponent(style)
            addDestinationIconSymbolLayer(style)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        showMessage(resources.getString(R.string.user_location_permission_explanation))
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            mapboxMap?.getStyle {
                enableLocationComponent(it)
            }
        } else {
            showMessage(resources.getString(R.string.user_location_permission_not_granted))
            finish()
        }
    }

    override fun onMapClick(point: LatLng): Boolean {
        val destinationPoint = Point.fromLngLat(point.longitude, point.latitude)
        val originPoint = Point.fromLngLat(
            locationComponent?.lastKnownLocation?.longitude!!,
            locationComponent?.lastKnownLocation?.latitude!!
        )
        val source = mapboxMap?.style?.getSourceAs<GeoJsonSource>(SOURCE_ID)
        source?.let {
            source.setGeoJson(Feature.fromGeometry(destinationPoint))
        }
        getRoute(originPoint, destinationPoint)
        return true
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    private fun enableLocationComponent(@NonNull loadedMapStyle: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            displayUserLocation(loadedMapStyle)
            initLocationEngine()
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager?.requestLocationPermissions(this)
        }
    }

    private fun addDestinationIconSymbolLayer(@NonNull loadedMapStyle: Style) {
        loadedMapStyle.addImage(
            ICON_ID,
            BitmapFactory.decodeResource(this.resources, R.drawable.mapbox_marker_icon_default)
        )
        val geoJsonSource = GeoJsonSource(SOURCE_ID)
        loadedMapStyle.addSource(geoJsonSource)
        val destinationSymbolLayer = SymbolLayer(LAYER_ID, SOURCE_ID)
        destinationSymbolLayer.withProperties(
            PropertyFactory.iconImage(ICON_ID),
            PropertyFactory.iconAllowOverlap(true),
            PropertyFactory.iconIgnorePlacement(true)
        )
        loadedMapStyle.addLayer(destinationSymbolLayer)
    }

    @SuppressLint("MissingPermission")
    private fun displayUserLocation(loadedMapStyle: Style) {
        locationComponent = mapboxMap?.locationComponent
        val locationOptions = LocationComponentActivationOptions.builder(this, loadedMapStyle)
            .build()
        locationComponent?.activateLocationComponent(locationOptions)
        locationComponent?.isLocationComponentEnabled = true
        locationComponent?.cameraMode = CameraMode.TRACKING
    }

    private fun getRoute(origin: Point, destination: Point) {
        NavigationRoute.builder(this)
            .accessToken(Mapbox.getAccessToken()!!)
            .origin(origin)
            .destination(destination)
            .build()
            .getRoute(object : Callback<DirectionsResponse> {

                override fun onResponse(call: Call<DirectionsResponse>, response: Response<DirectionsResponse>) {
                    response.body() ?: return
                    if (response.body()!!.routes().size < 1) return
                    val directionsRoute = response.body()!!.routes()[0]
                    startNavigation(directionsRoute)
                }

                override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                    showMessage(t.toString())
                }
            })
    }

    private fun startNavigation(directionsRoute: DirectionsRoute) {
        if (navigationMapRoute != null) {
            navigationMapRoute?.removeRoute()
        } else {
            navigationMapRoute = NavigationMapRoute(
                null,
                mapView!!,
                mapboxMap!!,
                R.style.NavigationMapRoute
            )
        }
        navigationMapRoute?.addRoute(directionsRoute)
    }

    @SuppressLint("MissingPermission")
    private fun initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this)
        val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
            .build()
        locationEngine?.requestLocationUpdates(request, callback, mainLooper)
        locationEngine?.getLastLocation(callback)
    }

    companion object {

        private const val SOURCE_ID = "destination-source-id"
        private const val ICON_ID = "destination-icon-id"
        private const val LAYER_ID = "destination-symbol-layer-id"

        private const val BUNDLE_LAT = "BUNDLE_LAT"
        private const val BUNDLE_LNG = "BUNDLE_LNG"

        private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 10000L
        private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5

        @JvmStatic
        fun getIntent(context: Context, desLatitude: Double, desLongitude: Double): Intent {
            val intent = Intent(context, DirectionPlaceActivity::class.java)
            val bundle = Bundle()
            bundle.putDouble(BUNDLE_LAT, desLatitude)
            bundle.putDouble(BUNDLE_LNG, desLongitude)
            intent.putExtras(bundle)

            return intent
        }
    }

    private inner class ActivityLocationCallback(activity: DirectionPlaceActivity) :
        LocationEngineCallback<LocationEngineResult> {

        private val activityWeakReference: WeakReference<DirectionPlaceActivity> = WeakReference(activity)

        override fun onSuccess(result: LocationEngineResult?) {
            val activity = activityWeakReference.get()
            if (activity != null) {
                val location = result?.lastLocation

                location ?: return

                if (activity.mapboxMap != null && result.lastLocation != null) {
                    activity.mapboxMap!!.locationComponent.forceLocationUpdate(result.lastLocation)
                    activity.onMapClick(activity.destinationPoint)
                }
            }
        }

        override fun onFailure(exception: Exception) {
            val activity = activityWeakReference.get()
            if (activity != null) {
                activity.showMessage(exception.localizedMessage)
            }
        }
    }
}
