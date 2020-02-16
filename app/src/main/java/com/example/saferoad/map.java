package com.example.saferoad;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class map extends Fragment implements OnMapReadyCallback {
    public static final int RequestPermissionCode = 1;
    public GoogleMap map;
    private LocationManager locationManager;
    private Marker mymarker;
    public LatLng lst;
    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mapview = inflater.inflate(R.layout.fragment_map, container, false);
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(getContext());
        locationManager=(LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,2,locationListenerGPS);
        }
        else {
            requestPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    lst=new LatLng(location.getLatitude(),location.getLongitude());
                    mymarker=map.addMarker(new MarkerOptions().position(lst).title("its me"));
                    CameraUpdate loc=CameraUpdateFactory.newLatLngZoom(lst,15);
                    map.animateCamera(loc);
                }
            }
        });
        return mapview;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map=googleMap;
        if(lst!=null) {
            mymarker = map.addMarker(new MarkerOptions().position(lst).title("added in onmapready"));
            CameraUpdate loc=CameraUpdateFactory.newLatLngZoom(lst,15);
            map.animateCamera(loc);
        }
        if (ContextCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        else {
            requestPermission();
        }
    }
    android.location.LocationListener locationListenerGPS=new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double lat=location.getLatitude();
            double lon=location.getLongitude();
            LatLng latLng=new LatLng(lat,lon);
            if(mymarker!=null) {
                mymarker.remove();
            }
            mymarker=map.addMarker(new MarkerOptions().position(latLng).title("its me"));
            //map.addCircle(new CircleOptions().center(latLng).radius(10).fillColor(10).strokeColor(Color.RED));
            Toast.makeText(getContext(), "location changed", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }
        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };
    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION,ACCESS_BACKGROUND_LOCATION}, RequestPermissionCode);
    }
}
