package com.ateam.codeathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class NewListingActivity extends AppCompatActivity {

    private TextView smallButton, mediumButton, largeButton, extraLargeButton;
    private EditText titleEditText, descEditText, priceEditText, timeEditText, locationEditText;
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selling_page);

        imgUri=Uri.parse(getIntent().getStringExtra("imageUri"));
        ImageView imageView = findViewById(R.id.selling_coverphoto);
        imageView.setImageURI(imgUri);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        smallButton = findViewById(R.id.selling_smallButton);
        mediumButton = findViewById(R.id.selling_mediumButton);
        largeButton = findViewById(R.id.selling_largeButton);
        extraLargeButton = findViewById(R.id.selling_extralargeButton);
        initializeSpinner();

        titleEditText = findViewById(R.id.selling_titleEditText);
        descEditText = findViewById(R.id.selling_descEditText);
        priceEditText = findViewById(R.id.selling_priceEditText);
        timeEditText = findViewById(R.id.selling_timeEditText);
        locationEditText = findViewById(R.id.selling_meetUpEditText);

        findViewById(R.id.selling_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.selling_doneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });

        smallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllSizeButtonUnselected();
                smallButton.setBackgroundResource(R.drawable.selling_size_selected);
                smallButton.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllSizeButtonUnselected();
                mediumButton.setBackgroundResource(R.drawable.selling_size_selected);
                mediumButton.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        largeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllSizeButtonUnselected();
                largeButton.setBackgroundResource(R.drawable.selling_size_selected);
                largeButton.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        extraLargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllSizeButtonUnselected();
                extraLargeButton.setBackgroundResource(R.drawable.selling_size_selected);
                extraLargeButton.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });

        findViewById(R.id.sellingpage_photo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(NewListingActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Allow user to upload multiple photos.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });

        findViewById(R.id.sellingpage_photo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(NewListingActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Allow user to upload multiple photos.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });

        findViewById(R.id.sellingpage_photo4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(NewListingActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Allow user to upload multiple photos.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });

    }

    private void setAllSizeButtonUnselected() {
        smallButton.setBackgroundResource(R.drawable.selling_size_unselected);
        mediumButton.setBackgroundResource(R.drawable.selling_size_unselected);
        largeButton.setBackgroundResource(R.drawable.selling_size_unselected);
        extraLargeButton.setBackgroundResource(R.drawable.selling_size_unselected);

        smallButton.setTextColor(ContextCompat.getColor(NewListingActivity.this, R.color.colorAccent));
        mediumButton.setTextColor(ContextCompat.getColor(NewListingActivity.this, R.color.colorAccent));
        largeButton.setTextColor(ContextCompat.getColor(NewListingActivity.this, R.color.colorAccent));
        extraLargeButton.setTextColor(ContextCompat.getColor(NewListingActivity.this, R.color.colorAccent));

    }

    private void initializeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.sellspinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, R.layout.selling_spinner);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.selling_spinner);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }

    // when clicked on the ticked. Now just simply show progress dialog for 1 sec
    // to show we are "posting"
    private void done() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Posting...");
        progress.setMessage("Posting your listing...");
        progress.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                Utils.setNumberOfItemPosted(NewListingActivity.this, Utils.getNumberOfItemPosted(NewListingActivity.this) + 1);
                String title= "title" + Utils.getNumberOfItemPosted(NewListingActivity.this);
                String profileName= "profileName" + Utils.getNumberOfItemPosted(NewListingActivity.this);
                String desc= "description" + Utils.getNumberOfItemPosted(NewListingActivity.this);
                String price= "price" + Utils.getNumberOfItemPosted(NewListingActivity.this);
                String location= "location" + Utils.getNumberOfItemPosted(NewListingActivity.this);
                String timing= "timing" + Utils.getNumberOfItemPosted(NewListingActivity.this);
                String imagePath= "imagePath" + Utils.getNumberOfItemPosted(NewListingActivity.this);
                Utils.setListing(imagePath, NewListingActivity.this, imgUri.toString());
                Utils.setListing(title, NewListingActivity.this, titleEditText.getText().toString());
                Utils.setListing(profileName, NewListingActivity.this, "Team Clutch");
                Utils.setListing(desc, NewListingActivity.this, descEditText.getText().toString());
                Utils.setListing(price, NewListingActivity.this, priceEditText.getText().toString());
                Utils.setListing(location, NewListingActivity.this, locationEditText.getText().toString());
                Utils.setListing(timing, NewListingActivity.this, timeEditText.getText().toString());
                progress.cancel();
                Toast.makeText(NewListingActivity.this, "Successfully posted!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 1000);
    }
}
