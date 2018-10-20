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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
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

    int imageIdResource [] = {R.drawable.sample1, R.drawable.sample2, R.drawable.sample3, R.drawable.sample4, R.drawable.sample5,
            R.drawable.sample1, R.drawable.sample1, R.drawable.sample1,};
    CharSequence profileName [] = {"", "", "", "", "", "", "", ""};
    CharSequence title [] = {"", "", "", "", "", "", "", ""};
    CharSequence desc [] = {"Desc 1", "Desc 2", "desc3 ", "desc4", "desc5", "6", "7", "8"};
    CharSequence price [] = {"", "", "", "", "", "", "", ""};
    CharSequence location [] = {"", "", "", "", "", "", "", ""};
    CharSequence timing [] = {"", "", "", "", "", "", "", ""};
    int[] imageId = {R.id.mainimageview_item1, R.id.mainimageview_item2, R.id.mainimageview_item3, R.id.mainimageview_item4, R.id.mainimageview_item5,
            R.id.mainimageview_item1, R.id.mainimageview_item1, R.id.mainimageview_item1,};
    int[] contactId = {R.id.contact_item1, R.id.contact_item2, R.id.contact_item3, R.id.contact_item4, R.id.contact_item5,
            R.id.contact_item1, R.id.contact_item1, R.id.contact_item1};
    int[] titleId = {R.id.titletextview_item1, R.id.titletextview_item2, R.id.titletextview_item3, R.id.titletextview_item4, R.id.titletextview_item5,
            R.id.titletextview_item1, R.id.titletextview_item1, R.id.titletextview_item1};



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

        findViewById(R.id.messageicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Go to message screen.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PINK)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });
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
        // coming back from newlistingactivity.java
        if (requestCode == 1010) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.e("ddd", "ggg");
                    setImageToSelected(R.id.homepage_home);
                    removeSearchLayout();
                    break;
                default:
                    break;
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
        navigation_view.getHeaderView(0).findViewById(R.id.viewmyprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Go to user profile page. User profile page shows user listings, histories, ratings etc.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_DEEP_ORANGE)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.tag1:
                        startActivity(new Intent(MainActivity.this, RewardActivity.class));
                        break;

                    case R.id.tag2:
                        SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                                .setText(Constants.NOT_YET_IMPLEMENTED + "Go to about page.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_TEAL)).setAnimations(Style.ANIMATIONS_POP).show();
                        break;
                    case R.id.tag3:
                        SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                                .setText(Constants.NOT_YET_IMPLEMENTED + "Go to How to use page.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN)).setAnimations(Style.ANIMATIONS_POP).show();
                        break;

                    case R.id.tag4:
                        SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                                .setText(Constants.NOT_YET_IMPLEMENTED + "Handle dispute between client and service providers").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED)).setAnimations(Style.ANIMATIONS_POP).show();
                        break;

                    case R.id.tag5:
                        SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                                .setText(Constants.NOT_YET_IMPLEMENTED + "Go to settings page").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_YELLOW)).setAnimations(Style.ANIMATIONS_POP).show();
                        break;

                    case R.id.tag6:
                        SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                                .setText(Constants.NOT_YET_IMPLEMENTED + "Rate this app on app store").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_ORANGE)).setAnimations(Style.ANIMATIONS_POP).show();
                        break;

                    case R.id.tag7:
                        SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                                .setText(Constants.NOT_YET_IMPLEMENTED + "Log user out").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREY)).setAnimations(Style.ANIMATIONS_POP).show();
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
                activityButtonClicked();
            }
        });
        ((ImageView) findViewById(R.id.homepage_mylisting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageToSelected(R.id.homepage_mylisting);
                removeSearchLayout();
                profileButtonClicked();
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
        findViewById(R.id.activitypage_textView).setVisibility(View.INVISIBLE);
        findViewById(R.id.profilepage_textView).setVisibility(View.INVISIBLE);
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

    private void activityButtonClicked() {
        findViewById(R.id.map).setVisibility(View.INVISIBLE);
        findViewById(R.id.activitypage_textView).setVisibility(View.VISIBLE);
    }

    private void profileButtonClicked() {
        findViewById(R.id.map).setVisibility(View.INVISIBLE);
        findViewById(R.id.profilepage_textView).setVisibility(View.VISIBLE);
    }

    // Everything is hardcoded to hack out this demo
    private void searchButtonClicked() {
        searchClicked = true;
        findViewById(R.id.messageicon).setVisibility(View.INVISIBLE);
        findViewById(R.id.map).setVisibility(View.INVISIBLE);
        findViewById(R.id.appname).setVisibility(View.INVISIBLE);
        findViewById(R.id.activitypage_textView).setVisibility(View.INVISIBLE);
        findViewById(R.id.profilepage_textView).setVisibility(View.INVISIBLE);


        findViewById(R.id.homepage_topsearch).setVisibility(View.VISIBLE);
        findViewById(R.id.searchword).setVisibility(View.VISIBLE);
        findViewById(R.id.searchLayout).setVisibility(View.VISIBLE);

        for(int i = 6; i <= Utils.getNumberOfItemPosted(this); i++) {
            switch (i) {
                case 6:
                    findViewById(R.id.item6).setVisibility(View.VISIBLE);
                    break;
                case 7:
                    findViewById(R.id.item7).setVisibility(View.VISIBLE);
                    break;
                case 8:
                    findViewById(R.id.item8).setVisibility(View.VISIBLE);
                    break;
            }
        }

        for(int i = 0; i < Utils.getNumberOfItemPosted(this); i++) {
            final int k = i;
            switch (i) {
                case 0:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item1)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item1)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item1)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item1)).getText();
                    imageIdResource[i] = R.drawable.sample1;
                    break;
                case 1:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item2)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item2)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item2)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item2)).getText();
                    imageIdResource[i] = R.drawable.sample2;
                    break;
                case 2:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item3)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item3)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item3)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item3)).getText();
                    imageIdResource[i] = R.drawable.sample3;
                    break;
                case 3:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item4)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item4)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item4)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item4)).getText();
                    imageIdResource[i] = R.drawable.sample4;
                    break;
                case 4:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item5)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item5)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item5)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item5)).getText();
                    imageIdResource[i] = R.drawable.sample5;
                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
            }
            findViewById(contactId[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                            .setText(Constants.NOT_YET_IMPLEMENTED + "Go to contact screen.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PINK)).setAnimations(Style.ANIMATIONS_POP).show();
                }
            });

            findViewById(imageId[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SingleItemActivity.class);
                    intent.putExtra("profileName", profileName[k]);
                    intent.putExtra("title", title[k]);
                    intent.putExtra("desc", desc[k]);
                    intent.putExtra("price", price[k]);
                    intent.putExtra("location", location[k]);
                    intent.putExtra("timing", timing[k]);
                    intent.putExtra("imageId", imageIdResource[k]);
                    Log.e("ddd", "before: " + profileName[k]);
                    startActivity(intent);
                }
            });

            findViewById(titleId[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SingleItemActivity.class);
                    intent.putExtra("profileName", profileName[k]);
                    intent.putExtra("title", title[k]);
                    intent.putExtra("desc", desc[k]);
                    intent.putExtra("price", price[k]);
                    intent.putExtra("location", location[k]);
                    intent.putExtra("timing", timing[k]);
                    intent.putExtra("imageId", imageIdResource[k]);
                    Log.e("ddd", "before: " + profileName[k]);
                    startActivity(intent);
                }
            });
        }

        EditText editText = findViewById(R.id.searchword);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Search function will filter result").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_LIGHT_GREEN)).setAnimations(Style.ANIMATIONS_POP).setGravity(Gravity.TOP).show();
            }
        });

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

    public void newItemPosted(String name, String title, ImageView image,
                                     String price, String time, String address) {
        int nameId, titleId, imageId, priceId, timeId, addressId;
        switch (Utils.getNumberOfItemPosted(this)) {
            case 6:
                nameId = R.id.profilenametextview_item6;
                titleId = R.id.titletextview_item6;
                imageId = R.id.mainimageview_item6;
                priceId = R.id.pricetextview_item6;
                timeId = R.id.timerequired_item6;
                addressId = R.id.locationtextview_item6;
                findViewById(R.id.item6).setVisibility(View.VISIBLE);
                break;
            case 7:
                nameId = R.id.profilenametextview_item7;
                titleId = R.id.titletextview_item7;
                imageId = R.id.mainimageview_item7;
                priceId = R.id.pricetextview_item7;
                timeId = R.id.timerequired_item7;
                addressId = R.id.locationtextview_item7;
                findViewById(R.id.item7).setVisibility(View.VISIBLE);
                break;
            case 8:
                nameId = R.id.profilenametextview_item8;
                titleId = R.id.titletextview_item8;
                imageId = R.id.mainimageview_item8;
                priceId = R.id.pricetextview_item8;
                timeId = R.id.timerequired_item8;
                addressId = R.id.locationtextview_item8;
                findViewById(R.id.item8).setVisibility(View.VISIBLE);
                break;
                default:
                    nameId = R.id.profilenametextview_item8;
                    titleId = R.id.titletextview_item8;
                    imageId = R.id.mainimageview_item8;
                    priceId = R.id.pricetextview_item8;
                    timeId = R.id.timerequired_item8;
                    addressId = R.id.locationtextview_item8;
        }

        ((TextView) findViewById(nameId)).setText(name);
        ((TextView) findViewById(titleId)).setText(title);
        ((ImageView) findViewById(imageId)).setImageResource(imageId);
        ((TextView) findViewById(priceId)).setText(price);
        ((TextView) findViewById(timeId)).setText(time);
        ((TextView) findViewById(addressId)).setText(address);
    }
}


