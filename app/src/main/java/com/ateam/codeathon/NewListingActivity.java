package com.ateam.codeathon;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewListingActivity extends AppCompatActivity {

    private TextView smallButton, mediumButton, largeButton, extraLargeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selling_page);

        Uri imgUri=Uri.parse(getIntent().getStringExtra("imageUri"));
        ImageView imageView = findViewById(R.id.selling_coverphoto);
        imageView.setImageURI(imgUri);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        smallButton = findViewById(R.id.selling_smallButton);
        mediumButton = findViewById(R.id.selling_mediumButton);
        largeButton = findViewById(R.id.selling_largeButton);
        extraLargeButton = findViewById(R.id.selling_extralargeButton);
        initializeSpinner();

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
                progress.cancel();
                Toast.makeText(NewListingActivity.this, "Successfully posted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 1000);
    }
}
