package com.openclassrooms.entrevoisins.ui_neighbour_details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

public class DetailsNeighbourActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);


        transfertParcelable();
        myBackImageButton();
        myFabFavoriteButton();


    }


    public void myFabFavoriteButton() {
        FloatingActionButton FabFavorite = findViewById(R.id.fab_favorite);
        FabFavorite.setOnClickListener(view -> FabFavorite.setBackgroundColor(getResources().getColor(R.color.colorGrey)));
    }


    public void transfertParcelable() {
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


    public void myBackImageButton() {
        ImageButton back = findViewById(R.id.btn_back);
        back.setOnClickListener(v -> onBackPressed());
    }


}
