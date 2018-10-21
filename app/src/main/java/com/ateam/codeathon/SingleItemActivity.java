package com.ateam.codeathon;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public class SingleItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemscreen);

        Intent intent = getIntent();

        findViewById(R.id.singleitem_backImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Log.e("ddd", "after: " + intent.getExtras().getString("profileName"));

        ((TextView) findViewById(R.id.singleitem_profileNameTextView)).setText(intent.getExtras().getString("profileName"));
        ((TextView) findViewById(R.id.singleitem_titleTextView)).setText(intent.getExtras().getString("title"));
        ((TextView) findViewById(R.id.singleitem_descriptionTextView)).setText(intent.getExtras().getString("desc"));
        ((TextView) findViewById(R.id.singleitem_moneyTextView)).setText(intent.getExtras().getString("price"));
        ((TextView) findViewById(R.id.singleitem_locationTextView)).setText(intent.getExtras().getString("location"));
        ((TextView) findViewById(R.id.singleitem_timerTextView)).setText(intent.getExtras().getString("timing"));
        ((ImageView) findViewById(R.id.singleitem_mainImage)).setImageResource(intent.getExtras().getInt("imageId"));
        int id = intent.getExtras().getInt("imagePath");

        if(Utils.getNumberOfItemPosted(SingleItemActivity.this) > 5 && id > 5) {
            String imagePath2 = "imagePath" + id;
            String path = Utils.getListing(imagePath2,SingleItemActivity.this);
            ((ImageView) findViewById(R.id.singleitem_mainImage)).setImageURI(Uri.parse(path));
        }
        findViewById(R.id.singleitem_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(SingleItemActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Go to message screen.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PINK)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });

        findViewById(R.id.singleitem_offer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(SingleItemActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Go to offer screen.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PINK)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });

        findViewById(R.id.singleItem_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(SingleItemActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Share this job link on Facebook").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });

        findViewById(R.id.singleItem_whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(SingleItemActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Share this job link on Whatsapp").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });

        findViewById(R.id.singleItem_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(SingleItemActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Go to share screen.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });

        findViewById(R.id.singleitem_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperActivityToast.create(SingleItemActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setText(Constants.NOT_YET_IMPLEMENTED + "Go to user profile.").setDuration(Style.DURATION_LONG).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_TEAL)).setAnimations(Style.ANIMATIONS_POP).show();
            }
        });
    }
}
