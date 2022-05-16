package com.example.ck.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ck.R;
import com.example.ck.directggmap.GMapV2Direction;
import com.example.ck.directggmap.GetDirectionsAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragment_map extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapview);
        SupportMapFragment.newInstance(options);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        VIETNAM = new LatLng(10.7950, 106.7219);
        mMap.addMarker(new MarkerOptions()
                .position(VIETNAM)
                .title("Shop sporting")
                .snippet("Cửa hàng bán áo thể thao độc nhất Việt Nam")
                .position(VIETNAM));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(VIETNAM, 18));

        DIEMDEN = new LatLng(10.7964,106.7244);
       // if()
        final GMapV2Direction md = new GMapV2Direction();
        // OnGetDataCompleteListener completeListener = null;
        findDirections(VIETNAM.latitude, VIETNAM.longitude,
                DIEMDEN.latitude, DIEMDEN.longitude,
                GMapV2Direction.MODE_DRIVING);

    }

    private static LatLng DIEMDEN;
    private static LatLng VIETNAM;
    private Polyline newPolyline;
    private LatLngBounds latlngBounds;
    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(
                Color.RED);

        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null) {
            newPolyline.remove();
        }
        newPolyline = mMap.addPolyline(rectLine);
        latlngBounds = createLatLngBoundsObject(VIETNAM, DIEMDEN);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
                latlngBounds, 400, 400, 150));

    }
    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation,
                                                  LatLng secondLocation) {
        if (firstLocation != null && secondLocation != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }

    public void findDirections(double fromPositionDoubleLat,
                               double fromPositionDoubleLong, double toPositionDoubleLat,
                               double toPositionDoubleLong, String mode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT,
                String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG,
                String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT,
                String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG,
                String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
        // asyncTask.cancel(true);
    }


}
