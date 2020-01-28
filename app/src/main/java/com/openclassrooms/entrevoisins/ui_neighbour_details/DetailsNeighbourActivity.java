package com.openclassrooms.entrevoisins.ui_neighbour_details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import butterknife.ButterKnife;

public class DetailsNeighbourActivity extends AppCompatActivity {

    boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);
        ButterKnife.bind(this);


        transfertExtras();
        configToolbar();
        myFabFavoriteButton();

    }


    public void myFabFavoriteButton() {
        FloatingActionButton fabFavorite = findViewById(R.id.fab_favorite);

        fabFavorite.setOnClickListener(view -> {

            if (isPressed) {
                fabFavorite.setImageResource(R.drawable.ic_star_border_black);
                //                removeFromFavorite();

            } else {
                fabFavorite.setImageResource(R.drawable.ic_star_favorite);
                //                addToFavorite();


            }
            isPressed = !isPressed;

        });
    }


    public void transfertExtras() {
        if (getIntent().hasExtra("Neighbour Detail")) {
            Neighbour neighbour = getIntent().getParcelableExtra("Neighbour Detail");

            String name = neighbour.getName();
            String avatar = neighbour.getAvatarUrl();

            TextView textview = findViewById(R.id.tv_name);
            textview.setText(name);

            TextView textview2 = findViewById(R.id.tv_name_photo);
            textview2.setText(name);

            ImageView imageView = findViewById(R.id.iv_photo);
            Glide.with(this).load(avatar).into(imageView);
        }
    }

    private void configToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_details);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
