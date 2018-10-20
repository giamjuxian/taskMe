package com.ateam.codeathon;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RewardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reward);

        final RelativeLayout myRewardButton = findViewById(R.id.reward_myrewardButton);
        final RelativeLayout catalogueButton = findViewById(R.id.reward_catalogueButton);
        final TextView myRewardText = findViewById(R.id.reward_myrewardText);
        final TextView catalogueText = findViewById(R.id.reward_catalogue_Text);

        final LinearLayout myReward_linearLayout = findViewById(R.id.reward_myrewardLayout);
        final LinearLayout catalogue_linearLayout = findViewById(R.id.reward_catalogueLayout);

        findViewById(R.id.reward_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRewardText.setTextColor(Color.parseColor("#FFFFFF"));
                myRewardButton.setBackgroundResource(R.drawable.reward_clickedleft);

                catalogueText.setTextColor(ContextCompat.getColor(RewardActivity.this, R.color.primarygreen));
                catalogueButton.setBackgroundResource(R.drawable.reward_myreward_2);

                myReward_linearLayout.setVisibility(View.VISIBLE);
                catalogue_linearLayout.setVisibility(View.INVISIBLE);
            }
        });

        catalogueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogueText.setTextColor(Color.parseColor("#FFFFFF"));
                catalogueButton.setBackgroundResource(R.drawable.reward_clickedright);

                myRewardText.setTextColor(ContextCompat.getColor(RewardActivity.this, R.color.primarygreen));
                myRewardButton.setBackgroundResource(R.drawable.reward_myreward_3);

                myReward_linearLayout.setVisibility(View.INVISIBLE);
                catalogue_linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
