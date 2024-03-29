package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MapFragment extends Fragment {
    private static final String TAG = "MapFragment";

    private LinearLayout listBranchLinearLayout;
    private ImageButton listBranchImageButton;
    protected boolean isBackButton;

    private ArrayList<Branch> listBranch;
    private ListView listView;
    // Google Map
    private GoogleMap GGMAP;
    private ArrayList<Marker> markerList;

    // Fire base
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference branchDatabaseref;

    // Location
    private FusedLocationProviderClient fusedLocationClient;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private int reloadUserLocationMaxCnt = 2;
    private LatLng currentUserLocation;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        listBranchLinearLayout = view.findViewById(R.id.list_branch_LinearLayout);
        listBranchImageButton = view.findViewById(R.id.list_branch_ImageButton);
        ImageButton myLocationImageButton = view.findViewById(R.id.my_location_ImageButton);
        listView = view.findViewById(R.id.list_branch_ListView);

        isBackButton = true;

        firebaseDatabase = FirebaseDatabase.getInstance();
        branchDatabaseref = firebaseDatabase.getReference("Branches");
        listBranch = new ArrayList<>();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        currentUserLocation = new LatLng(0, 0);

        branchDatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Branch branch = dataSnapshot.getValue(Branch.class);
                    listBranch.add(branch);

                    if (GGMAP != null) {
                        addMarkers(branch);
                    }
                }
//                ArrayList<Branch> list = new ArrayList<>();
//                Branch branch1 = new Branch("Bookaholic Nguyễn Văn Cừ", "0905123456", "9:00","21:00",10.762222591092831, 106.68272929761598, 4.5F);
//                Branch branch2 = new Branch("Bookaholic Thủ Đức", "0906654321", "8:00","20:00",10.875761989681283, 106.79914843994409, 5.0F);
//                list.add(branch1);
//                list.add(branch2);
//                for(int i = 0; i< list.size(); i++) {
//                    branchDatabaseref.push().setValue(list.get(i));
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: can not fetch data");
            }
        });

        listBranchImageButton.setOnClickListener(v -> changeBackButton());

        BranchListViewAdapter adapter = new BranchListViewAdapter(listBranch);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            Branch branch = (Branch) adapter.getItem(i);
            LatLng newFocus = new LatLng(branch.getLatitude(), branch.getLongitude());
            focusMap(newFocus, 16);
            isBackButton = false;
            changeBackButton();

        });

        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                    for (Map.Entry<String, Boolean> entry : isGranted.entrySet()) {
                        if (!entry.getValue()) {
                            Toast.makeText(getContext(), "Denied", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Toast.makeText(getContext(), "Granted", Toast.LENGTH_SHORT).show();
                    if (isLocationEnable()) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                                Location location = task.getResult();
                                if (location != null) {
                                    reloadUserLocationMaxCnt = 2;

                                    if ((location.getLatitude() != currentUserLocation.latitude)
                                            || (location.getLongitude() != currentUserLocation.longitude)) {
                                        for (Marker i : markerList) {
                                            if (i.equals(currentUserLocation)) {
                                                Toast.makeText(getContext(), "Removed the old location", Toast.LENGTH_SHORT).show();
                                                markerList.remove(i);
                                                i.remove();
                                                break;
                                            }
                                        }
                                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                        currentUserLocation = new LatLng(userLocation.latitude, userLocation.longitude);
                                        Marker newMarker = GGMAP.addMarker(new MarkerOptions()
                                                .title("Your Location")
                                                .position(userLocation)
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                        markerList.add(newMarker);
                                    }
                                    focusMap(currentUserLocation, 16);
                                } else {
                                    if (reloadUserLocationMaxCnt == 0) {
                                        return;
                                    }
                                    reloadUserLocationMaxCnt -= 1;
                                    requestUserLocation();
                                }
                            });}
                    }
                    else {
                        Toast.makeText(getContext(), "Please enable location", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }

                });

        myLocationImageButton.setOnClickListener(view12 -> requestUserLocation());



        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.ggmap_fragment);

        supportMapFragment.getMapAsync(googleMap -> {
            GGMAP = googleMap;
            markerList = new ArrayList<>();

            if (getResources().getBoolean(R.bool.isDarkMode)) {
                GGMAP.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.style_json_night));
            }
            else {
                GGMAP.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.style_json_day));
            }

            GGMAP.clear();
            LatLng defaultPos = new LatLng(10.764788611278338, 106.67918051020337);
            GGMAP.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    defaultPos, 16
            ));

            if (listBranch.size() != 0) {
                for (Branch branch : listBranch) {
                    addMarkers(branch);
                }
            }
        });

        return view;
    }

    private void requestUserLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)  {
            if (isLocationEnable()) {
                fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();

                    if (location != null) {
                        reloadUserLocationMaxCnt = 2;

                        if ((location.getLatitude() != currentUserLocation.latitude)
                                || (location.getLongitude() != currentUserLocation.longitude)) {
                            for (Marker i : markerList) {
                                if (i.equals(currentUserLocation)) {
                                    Toast.makeText(getContext(), "remove old location", Toast.LENGTH_SHORT).show();
                                    markerList.remove(i);
                                    i.remove();
                                    break;
                                }
                            }
                            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            currentUserLocation = new LatLng(userLocation.latitude, userLocation.longitude);
                            Marker newMarker = GGMAP.addMarker(new MarkerOptions()
                                    .title("Your Location")
                                    .position(userLocation)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            markerList.add(newMarker);
                        }
                        focusMap(currentUserLocation, 16);
                    }
                    else {
                        if (reloadUserLocationMaxCnt == 0) {
                            return;
                        }
                        reloadUserLocationMaxCnt -= 1;
                        requestUserLocation();
                    }
                });
            }
            else {
                Toast.makeText(getContext(), "Please enable location", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

        }
        else {
            requestPermissionLauncher.launch(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
        }
    }

    private boolean isLocationEnable() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void focusMap(LatLng location, int zoom) {
        GGMAP.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
    }

    private void addMarkers(Branch markers) {
        @SuppressLint("UseCompatLoadingForDrawables") Marker newMarker = GGMAP.addMarker(new MarkerOptions()
                .position(new LatLng(markers.getLatitude(), markers.getLongitude()))
                .title(markers.getName())
                .icon(BitmapDescriptorFactory.fromBitmap(
                        Bitmap.createScaledBitmap(
                                ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_branch)).getBitmap(),
                                100, 100, false))
                ));
        markerList.add(newMarker);
    }
    private void changeBackButton() {
        if (isBackButton) {
            listBranchLinearLayout.setVisibility(View.VISIBLE);
            listBranchImageButton.setImageResource(R.drawable.ic_back_button);
            isBackButton = false;
        }
        else {
            listBranchLinearLayout.setVisibility(View.GONE);
            listBranchImageButton.setImageResource(R.drawable.ic_baseline_menu_24);
            isBackButton = true;
        }
    }

}
