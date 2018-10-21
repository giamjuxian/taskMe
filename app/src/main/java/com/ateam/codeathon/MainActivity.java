package com.ateam.codeathon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Random;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private GoogleMap mMap;
    private Random rand;
    private boolean searchClicked = false;

    int imageIdResource [] = {R.drawable.hdbroom, R.drawable.dogstock, R.drawable.pizzastock, R.drawable.chipsstock, R.drawable.hdb2,
            R.drawable.sample1, R.drawable.sample1, R.drawable.sample1,};
    CharSequence profileName [] = {"", "", "", "", "", "", "", ""};
    CharSequence title [] = {"", "", "", "", "", "", "", ""};
    CharSequence desc [] = {"Need help to clean my living room! For 3 hrs, I will be paying $60. It will be on October 25 4pm-7pm. Let me know if you are interested!",
            "Need your help to walk my dog as I have been very busy recently. He is very obedient, and will not bark at other people. Pleasant dog!",
            "Teach me to make pizza! I want to learn to make pizza for my girlfriend. I am looking at Cheese Pizza. Let me know if you are interested!",
            "I am craving for potato chips. Please get me Lays Barbeque chips. 2 big pack. Willing to pay you $5 on top of the price for the chips.",
            "Clean my kitchen for me. It is a typical HDB 4-room flat kitchen size. Job is on October 26 2-6pm. Willing to pay $60 for the whole duration.", "6", "7", "8"};
    CharSequence price [] = {"", "", "", "", "", "", "", ""};
    CharSequence location [] = {"", "", "", "", "", "", "", ""};
    CharSequence timing [] = {"", "", "", "", "", "", "", ""};
    int[] imageId = {R.id.mainimageview_item1, R.id.mainimageview_item2, R.id.mainimageview_item3, R.id.mainimageview_item4, R.id.mainimageview_item5,
            R.id.mainimageview_item6, R.id.mainimageview_item7, R.id.mainimageview_item8,};
    int[] contactId = {R.id.contact_item1, R.id.contact_item2, R.id.contact_item3, R.id.contact_item4, R.id.contact_item5,
            R.id.contact_item6, R.id.contact_item7, R.id.contact_item8};
    int[] titleId = {R.id.titletextview_item1, R.id.titletextview_item2, R.id.titletextview_item3, R.id.titletextview_item4, R.id.titletextview_item5,
            R.id.titletextview_item6, R.id.titletextview_item7, R.id.titletextview_item8};

    String [] imagePath = {"", "", "", "", "", "", "", ""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        setupDrawer();
        setupNavigation();

        rand = new Random();

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
        setUpMap();
        mapSpinner();
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

        addMapMarker();

        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(1.2966,103.7764) , 14.0f) );
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMarkerClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
            return;
        }

        mMap.setMyLocationEnabled(true);
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
                switch (position) {
                    case 0:
                        removeMapMarker();
                        addMapMarker();
                        break;
                    case 1:
                        removeMapMarker();
                        addBabySittingMarker();
                        break;
                    case 2:
                        removeMapMarker();
                        addCleaningMarker();
                        break;
                    case 3:
                        removeMapMarker();
                        addCookingMarker();
                        break;
                    case 4:
                        removeMapMarker();
                        addGroceryShoppingMarker();
                        break;
                    case 5:
                        removeMapMarker();
                        addPetsMarker();
                        break;
                    case 6:
                        removeMapMarker();
                        addOthersMarker();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addMapMarker() {
        addBabySittingMarker();
        addCleaningMarker();
        addCookingMarker();
        addGroceryShoppingMarker();
        addPetsMarker();
        addOthersMarker();
    }

    private void removeMapMarker() {
        mMap.clear();
    }


    private float randomFloat(float min, float max) {
        return rand.nextFloat() * (max - min) + min;

    }

    private void addBabySittingMarker() {
        for(int i = 0; i < 20; i++) {
            Marker marker =
                    mMap.addMarker(new MarkerOptions().position(new LatLng(randomFloat(1.3f, 1.4f), randomFloat(103.67f, 103.95f)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.baby)));
            marker.setTag(Category.BABY_SITTING);

        }
    }

    private void addCleaningMarker() {
        for(int i = 0; i < 30; i++) {
            Marker marker =
                mMap.addMarker(new MarkerOptions().position(new LatLng(randomFloat(1.3f, 1.4f), randomFloat(103.67f, 103.95f)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cleaning)));
            marker.setTag(Category.CLEANING);
        }
    }

    private void addCookingMarker() {
        for(int i = 0; i < 30; i++) {
            Marker marker =
                    mMap.addMarker(new MarkerOptions().position(new LatLng(randomFloat(1.3f, 1.4f), randomFloat(103.67f, 103.95f)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cooking)));
            marker.setTag(Category.COOKING);
        }
    }

    private void addGroceryShoppingMarker() {
        for(int i = 0; i < 30; i++) {
            Marker marker =
                    mMap.addMarker(new MarkerOptions().position(new LatLng(randomFloat(1.3f, 1.4f), randomFloat(103.67f, 103.95f)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.shopping)));
            marker.setTag(Category.GROCERY_SHOPPING);
        }
    }

    private void addPetsMarker() {
        for(int i = 0; i < 30; i++) {
            Marker marker =
                    mMap.addMarker(new MarkerOptions().position(new LatLng(randomFloat(1.3f, 1.4f), randomFloat(103.67f, 103.95f)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dog)));
            marker.setTag(Category.WALK_PETS);
        }
    }

    private void addOthersMarker() {
        for(int i = 0; i < 30; i++) {
            Marker marker =
                    mMap.addMarker(new MarkerOptions().position(new LatLng(randomFloat(1.3f, 1.4f), randomFloat(103.67f, 103.95f)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.other)));
            marker.setTag(Category.OTHERS);
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

                }
                break;

            case 2:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    setImageToSelected(R.id.homepage_home);
                    removeSearchLayout();
                }
                else {
                    setImageToSelected(R.id.homepage_sell);
                    removeSearchLayout();
                    sell();
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
            String title2= "title" + i;
            String profileName2= "profileName" + i;
            String description2 = "description" + i;
            String price2 = "price" + i;
            String location2 = "location" + i;
            String timing2 = "timing" + i;
            String imagePath2 = "imagePath" + i;

            title[i - 1] = Utils.getListing(title2,MainActivity.this);
            profileName[i - 1] = Utils.getListing(profileName2,MainActivity.this);
            price[i - 1] = Utils.getListing(price2,MainActivity.this);
            location[i - 1] = Utils.getListing(location2,MainActivity.this);
            timing[i-1] = Utils.getListing(timing2,MainActivity.this);
            desc[i-1] = Utils.getListing(description2,MainActivity.this);
            imagePath[i-1] = Utils.getListing(imagePath2,MainActivity.this);

            newItemPosted(i, (String) profileName[i-1], (String) title[i-1], (String) price[i-1], (String) timing[i-1], (String) location[i-1]);
Log.e("ddd", "gg " + imagePath[i-1]);
Log.e("ddd", "gg2 " + Utils.getListing(imagePath2,MainActivity.this));
            switch (i) {
                case 6:
                    findViewById(R.id.item6).setVisibility(View.VISIBLE);
//                    String title= "title" + Utils.getNumberOfItemPosted(MainActivity.this);
//                    String profileName= "profileName" + Utils.getNumberOfItemPosted(MainActivity.this);
//                    String description= "description" + Utils.getNumberOfItemPosted(MainActivity.this);
//                    String price= "price" + Utils.getNumberOfItemPosted(MainActivity.this);
//                    String location= "location" + Utils.getNumberOfItemPosted(MainActivity.this);
//                    String timing= "timing" + Utils.getNumberOfItemPosted(MainActivity.this);
//                    String imagePath= "imagePath" + Utils.getNumberOfItemPosted(MainActivity.this);
//                    ((TextView) findViewById(R.id.profilenametextview_item6)).setText(Utils.getListing(profileName, MainActivity.this));
//                    ((TextView) findViewById(R.id.titletextview_item6)).setText(Utils.getListing(title, MainActivity.this));
//                    desc[5] = Utils.getListing(description, MainActivity.this);
//                    ((TextView) findViewById(R.id.pricetextview_item6)).setText(Utils.getListing(price, MainActivity.this));
//                    ((TextView) findViewById(R.id.locationtextview_item6)).setText(Utils.getListing(location, MainActivity.this));
//                    ((TextView) findViewById(R.id.timerequired_item6)).setText(Utils.getListing(timing, MainActivity.this));
                    ((ImageView) findViewById(R.id.mainimageview_item6)).setImageURI(Uri.parse(imagePath[i-1]));

                    break;
                case 7:
                    findViewById(R.id.item7).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.mainimageview_item7)).setImageURI(Uri.parse(imagePath[i-1]));
                    break;
                case 8:
                    findViewById(R.id.item8).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.mainimageview_item8)).setImageURI(Uri.parse(imagePath[i-1]));

                    break;
            }
        }
Log.e("ddd", "g: " + Utils.getNumberOfItemPosted(this));
        for(int i = 0; i < Utils.getNumberOfItemPosted(this); i++) {
            final int k = i;
            switch (i) {
                case 0:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item1)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item1)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item1)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item1)).getText();
                    imageIdResource[i] = R.drawable.hdbroom;
                    break;
                case 1:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item2)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item2)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item2)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item2)).getText();
                    imageIdResource[i] = R.drawable.dogstock;
                    break;
                case 2:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item3)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item3)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item3)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item3)).getText();
                    imageIdResource[i] = R.drawable.pizzastock;
                    break;
                case 3:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item4)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item4)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item4)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item4)).getText();
                    imageIdResource[i] = R.drawable.chipsstock;
                    break;
                case 4:
                    title[i] = ((TextView) findViewById(titleId[i])).getText();
                    profileName[i] = ((TextView) findViewById(R.id.profilenametextview_item5)).getText();
                    price[i] = ((TextView) findViewById(R.id.pricetextview_item5)).getText();
                    location[i] = ((TextView) findViewById(R.id.locationtextview_item5)).getText();
                    timing[i] = ((TextView) findViewById(R.id.timerequired_item5)).getText();
                    imageIdResource[i] = R.drawable.hdb2;
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
                    intent.putExtra("imagePath", k + 1);
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
                    intent.putExtra("imagePath", k + 1);
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

    public void newItemPosted(int index, String name, String title,
                                     String price, String time, String address) {
        int nameId, titleId, imageId, priceId, timeId, addressId;
        switch (index) {
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        Category category = (Category) marker.getTag();
        Intent intent = new Intent(MainActivity.this, SingleItemActivity.class);
        TempInfo tempInfo = new TempInfo(Category.COOKING,rand.nextInt(TempInfo.size));
        switch (category) {
            case COOKING:
                tempInfo = new TempInfo(Category.COOKING,rand.nextInt(TempInfo.size));
                break;
            case BABY_SITTING:
                tempInfo = new TempInfo(Category.BABY_SITTING,rand.nextInt(TempInfo.size));
                break;
            case CLEANING:
                tempInfo = new TempInfo(Category.CLEANING,rand.nextInt(TempInfo.size));
                break;
            case GROCERY_SHOPPING:
                tempInfo = new TempInfo(Category.GROCERY_SHOPPING,rand.nextInt(TempInfo.size));
                break;
            case WALK_PETS:
                tempInfo = new TempInfo(Category.WALK_PETS,rand.nextInt(TempInfo.size));
                break;
            case OTHERS:
                tempInfo = new TempInfo(Category.OTHERS,rand.nextInt(TempInfo.size));
                break;
        }

        intent.putExtra("profileName", tempInfo.getProfileName());
        intent.putExtra("title", tempInfo.getTitle());
        intent.putExtra("desc", tempInfo.getDesc());
        intent.putExtra("price", tempInfo.getPrice());
        intent.putExtra("location", tempInfo.getLocation());
        intent.putExtra("timing", tempInfo.getTiming());
        intent.putExtra("imageId", tempInfo.getImageId());
        startActivity(intent);
        return false;
    }
}

class TempInfo {
    String [] profileName = {"Addison Ho"};
    String [] title = {"Need help to learn how to cook!"};
    String [] desc = {"I want to learn how to cook pasta for my girlfriend. Please assist!"};
    String [] price = {"$50"};
    String [] location = {"41 Clementi Avenue 1"};
    String [] timing = {"October 21 2-4pm"};
    int [] imageId = {R.drawable.pasta};

    String [] profileNameBaby = {"Jack Chan"};
    String [] titleBaby = {"Need help to help baby sit 2yrs old."};
    String [] descBaby = {"I need help in baby sitting a 2yrs old kid for a day. Contact me if interested."};
    String [] priceBaby = {"$50"};
    String [] locationBaby = {"Jurong Easy"};
    String [] timingBaby = {"October 21"};
    int [] imageIdBaby = {R.drawable.baby};

    String [] profileNameCleaning = {"Jasmine Lyn"};
    String [] titleCleaning = {"Looking for helper to clean my living room"};
    String [] descCleaning = {"Just moved house. Need help to clean the living room. Pm me if interested."};
    String [] priceCleaning = {"$50"};
    String [] locationCleaning = {"Pioneer"};
    String [] timingCleaning = {"October 21 2-4pm"};
    int [] imageIdCleaning = {R.drawable.hdbroom};

    String [] profileNameShopping = {"Patty"};
    String [] titleShopping = {"Help me buy some potato chips and soft drinks."};
    String [] descShopping = {"Help me buy some chips and soft drinks by tonight. For party uses."};
    String [] priceShopping = {"$10"};
    String [] locationShopping = {"NUS Sheares Hall"};
    String [] timingShopping = {"October 21 7pm"};
    int [] imageIdShopping = {R.drawable.chipsstock};

    String [] profileNamePets = {"Yeslyn95"};
    String [] titlePets = {"Wash my pet for me"};
    String [] descPets = {"No time to wash my dog, hence need your help. He is very obedient and will not bark."};
    String [] pricePet = {"$50"};
    String [] locationPets = {"Boon Lay"};
    String [] timingPets = {"October 21 2pm"};
    int [] imageIdPets = {R.drawable.dogstock2};

    String [] profileNameOthers = {"Kelly"};
    String [] titleOthers = {"[London] Buy adidas bag for me"};
    String [] descOthers = {"If you are going London, need your help to buy an adidas bag."};
    String [] priceOthers = {"$100"};
    String [] locationOthers = {"London"};
    String [] timingOthers = {"October 22"};
    int [] imageIdOthers = {R.drawable.bagstock};
    public static int size = 1;

    private int id;
    private Category category;

    public TempInfo(Category type, int id) {
        this.id = id;
        this.category = type;
    }

    public String getProfileName() {
        switch (category) {
            case COOKING:
                return profileName[id];
            case BABY_SITTING:
                return profileNameBaby[id];
            case CLEANING:
                return profileNameCleaning[id];
            case WALK_PETS:
                return profileNamePets[id];
            case GROCERY_SHOPPING:
                return profileNameShopping[id];
            case OTHERS:
                return profileNameOthers[id];
        }
        return profileName[id];
    }

    public String getTitle() {
        switch (category) {
            case COOKING:
                return title[id];
            case BABY_SITTING:
                return titleBaby[id];
            case CLEANING:
                return titleCleaning[id];
            case WALK_PETS:
                return titlePets[id];
            case GROCERY_SHOPPING:
                return titleShopping[id];
            case OTHERS:
                return titleOthers[id];
        }
        return title[id];
    }

    public String getDesc() {
        switch (category) {
            case COOKING:
                return desc[id];
            case BABY_SITTING:
                return descBaby[id];
            case CLEANING:
                return descCleaning[id];
            case WALK_PETS:
                return descPets[id];
            case GROCERY_SHOPPING:
                return descShopping[id];
            case OTHERS:
                return descOthers[id];
        }
        return desc[id];
    }

    public String getPrice() {
        switch (category) {
            case COOKING:
                return price[id];
            case BABY_SITTING:
                return priceBaby[id];
            case CLEANING:
                return priceCleaning[id];
            case WALK_PETS:
                return pricePet[id];
            case GROCERY_SHOPPING:
                return priceShopping[id];
            case OTHERS:
                return priceOthers[id];
        }
        return price[id];
    }

    public String getLocation() {
        switch (category) {
            case COOKING:
                return location[id];
            case BABY_SITTING:
                return locationBaby[id];
            case CLEANING:
                return locationCleaning[id];
            case WALK_PETS:
                return locationPets[id];
            case GROCERY_SHOPPING:
                return locationShopping[id];
            case OTHERS:
                return locationOthers[id];
        }
        return location[id];
    }

    public String getTiming() {
        switch (category) {
            case COOKING:
                return timing[id];
            case BABY_SITTING:
                return timingBaby[id];
            case CLEANING:
                return timingCleaning[id];
            case WALK_PETS:
                return timingPets[id];
            case GROCERY_SHOPPING:
                return timingShopping[id];
            case OTHERS:
                return timingOthers[id];
        }
        return timing[id];
    }

    public int getImageId() {
        switch (category) {
            case COOKING:
                return imageId[id];
            case BABY_SITTING:
                return imageIdBaby[id];
            case CLEANING:
                return imageIdCleaning[id];
            case WALK_PETS:
                return imageIdPets[id];
            case GROCERY_SHOPPING:
                return imageIdShopping[id];
            case OTHERS:
                return imageIdOthers[id];
        }
        return imageId[id];
    }
}
