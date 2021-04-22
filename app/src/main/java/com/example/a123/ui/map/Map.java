package com.example.a123.ui.map;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a123.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Map extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private LatLngBounds ADELAIDE;
    private LatLng MyHome, School;

    @Override
    public View onCreateView( LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMinZoomPreference(10.0f);
        map.setMaxZoomPreference(20.0f);

        MyHome = new LatLng(46.379060, 48.052396);
        School = new LatLng(46.367432, 48.053248);
        map.addMarker(new MarkerOptions().position(MyHome).title("Мой дом").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        map.addMarker(new MarkerOptions().position(School).title("Лицей").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).snippet("Астраханский технический лицей"));

        PolylineOptions polylineOptions = new PolylineOptions()
                .add(MyHome).add(new LatLng(46.379133, 48.052188)).add(new LatLng(46.369052, 48.050528))
                .add(new LatLng(46.368933, 48.052316)).add(new LatLng(46.367504, 48.052305)).add(School)
                .color(Color.RED).width(8);
        map.addPolyline(polylineOptions);

        LatLngBounds australiaBounds = new LatLngBounds(
                new LatLng(46.256399, 47.927860),
                new LatLng(46.487400, 48.192554)
        );
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(australiaBounds, 0));
        ADELAIDE = new LatLngBounds(
                new LatLng(46.256399, 47.927860), new LatLng(46.487400, 48.192554));
        map.setLatLngBoundsForCameraTarget(ADELAIDE);
    }
}