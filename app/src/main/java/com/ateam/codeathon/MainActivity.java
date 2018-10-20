package com.ateam.codeathon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private GoogleMap mMap;
    private Random rand;
    private boolean searchClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        setupDrawer();
        setupNavigation();

        rand = new Random();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission
                            (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 1); // 1 is requestCode
                return;

            }
        }
        setUpMap();
        mapSpinner();

        setAllBottomIconToGray();
        setImageToSelected(R.id.homepage_home);
        setBottomBarClickable();
    }

    private void setupActionBar() {
        findViewById(R.id.homePageHamburger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });
    }

    private void toggleDrawer() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(1.358639,103.84525299999996) , 14.0f) );
        addMapMarker();
    }

    private void mapSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.map_array, R.layout.map_spinner);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.map_spinner);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    removeMapMarker();
                    addMapMarker();
                } else {
                    removeMapMarker();
                    addListingMarker();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void addMapMarker() {
        addGreenMarker();
        addRedMarker();
    }

    private void removeMapMarker() {
        mMap.clear();
    }

    private void addListingMarker() {
        for(int i = 0; i < 100; i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(randomFloat(1.3f, 1.4f), randomFloat(103.67f, 103.95f)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.listingicon)));
        }
    }

    private float randomFloat(float min, float max) {
        return rand.nextFloat() * (max - min) + min;

    }

    private void addGreenMarker() {
        for(int i = 0; i < 150; i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(randomFloat(1.3f, 1.4f), randomFloat(103.67f, 103.95f)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.recycgreen)));
        }

    }

    private void addRedMarker() {
        for(int i = 0; i < 60; i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(randomFloat(1.3f, 1.4f), randomFloat(103.67f, 103.95f)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.recycred)));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            CropImage.activity(imageUri).setAutoZoomEnabled(true).setInitialCropWindowPaddingRatio(0)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Intent localIntent = new Intent(getApplicationContext(), NewListingActivity.class);
                localIntent.putExtra("imageUri", resultUri.toString()); // send the image as intent to ImageEditorActivity.class
                startActivityForResult(localIntent, 1010);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){

                }
                else {
                    // permission granted do


                }
                break;
        }
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void setupNavigation() {
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        navigation_view.setItemIconTintList(null);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.tag1:
                        startActivity(new Intent(MainActivity.this, RewardActivity.class));
                        break;

                    case R.id.tag3:

                        break;

                    case R.id.tag4:

                        break;

                    case R.id.tag5:

                        break;



                }
                return false;
            }


        });
    }

    private void setAllBottomIconToGray() {
        ((ImageView) findViewById(R.id.homepage_home)).setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.verylightgray), PorterDuff.Mode.SRC_ATOP));
        ((ImageView) findViewById(R.id.homepage_search)).setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.verylightgray), PorterDuff.Mode.SRC_ATOP));
        ((ImageView) findViewById(R.id.homepage_sell)).setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.verylightgray), PorterDuff.Mode.SRC_ATOP));
        ((ImageView) findViewById(R.id.homepage_activity)).setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.verylightgray), PorterDuff.Mode.SRC_ATOP));
        ((ImageView) findViewById(R.id.homepage_mylisting)).setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.verylightgray), PorterDuff.Mode.SRC_ATOP));
    }

    private void setImageToSelected(int image) {
        setAllBottomIconToGray();
        ((ImageView) findViewById(image)).setColorFilter(new PorterDuffColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP));
    }

    private void setBottomBarClickable() {
        ((ImageView) findViewById(R.id.homepage_home)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageToSelected(R.id.homepage_home);
                removeSearchLayout();
            }
        });
        ((ImageView) findViewById(R.id.homepage_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageToSelected(R.id.homepage_search);
                searchButtonClicked();
            }
        });
        ((ImageView) findViewById(R.id.homepage_sell)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageToSelected(R.id.homepage_sell);
                removeSearchLayout();
                sell();
            }
        });
        ((ImageView) findViewById(R.id.homepage_activity)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageToSelected(R.id.homepage_activity);
                removeSearchLayout();
            }
        });
        ((ImageView) findViewById(R.id.homepage_mylisting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageToSelected(R.id.homepage_mylisting);
                removeSearchLayout();
            }
        });
    }

    private void sell() {
        if(isStoragePermissionGranted()) {
            CropImage.startPickImageActivity(MainActivity.this);
        }


    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    private void removeSearchLayout() {
        if(searchClicked) {
            searchClicked = false;
            findViewById(R.id.homepage_topsearch).setVisibility(View.INVISIBLE);
            findViewById(R.id.searchword).setVisibility(View.INVISIBLE);
            findViewById(R.id.searchLayout).setVisibility(View.INVISIBLE);

            findViewById(R.id.messageicon).setVisibility(View.VISIBLE);
            findViewById(R.id.map).setVisibility(View.VISIBLE);
            findViewById(R.id.appname).setVisibility(View.VISIBLE);

        }
    }

    private void searchButtonClicked() {
        searchClicked = true;
        findViewById(R.id.messageicon).setVisibility(View.INVISIBLE);
        findViewById(R.id.map).setVisibility(View.INVISIBLE);
        findViewById(R.id.appname).setVisibility(View.INVISIBLE);


        findViewById(R.id.homepage_topsearch).setVisibility(View.VISIBLE);
        findViewById(R.id.searchword).setVisibility(View.VISIBLE);
        findViewById(R.id.searchLayout).setVisibility(View.VISIBLE);


        Spinner spinner = (Spinner) findViewById(R.id.searchspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filterby_array, R.layout.searchspinner);
        adapter.setDropDownViewResource(R.layout.searchspinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        Spinner spinner2 = (Spinner) findViewById(R.id.sizespinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.size_array, R.layout.searchspinner);
        adapter2.setDropDownViewResource(R.layout.searchspinner);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(0);
    }
}


