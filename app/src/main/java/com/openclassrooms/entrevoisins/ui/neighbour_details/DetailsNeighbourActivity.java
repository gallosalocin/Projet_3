package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsNeighbourActivity extends AppCompatActivity {

    @BindView(R.id.iv_photo)
    ImageView photo;
    @BindView(R.id.tv_name_photo)
    TextView titleName;
    @BindView(R.id.tv_name)
    TextView detailName;
    @BindView(R.id.fab_favorite)
    FloatingActionButton fabFavorite;
    @BindView(R.id.tv_address)
    TextView detailAddress;
    @BindView(R.id.tv_phone)
    TextView detailPhoneNumber;
    @BindView(R.id.tv_website)
    TextView detailWebSite;
    @BindView(R.id.tv_about_me_details)
    TextView detailAboutMe;
    @BindView(R.id.tb_details)
    Toolbar toolbar;


    private NeighbourApiService apiService;
    private Neighbour neighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);
        ButterKnife.bind(this);
        apiService = DI.getNeighbourApiService();

        onGetExtras();
        configToolbar();
        myFabFavoriteButton();
    }

    public void myFabFavoriteButton() {
        fabFavorite.setOnClickListener(view -> {
            apiService.addNeighbourAsFavorite(neighbour);
            neighbour.setIsFavorite(!neighbour.getIsFavorite());
            fabFavorite.setImageResource(neighbour.getIsFavorite() ? R.drawable.ic_star_favorite : R.drawable.ic_star_border_black);
        });
    }

    private void onGetExtras() {
        if (getIntent().hasExtra("Neighbour")) {

            neighbour = getIntent().getParcelableExtra("Neighbour");

            Glide.with(this).load(neighbour.getAvatarUrl()).into(photo);
            titleName.setText(neighbour.getName());
            detailName.setText(neighbour.getName());
            detailAddress.setText(neighbour.getAddress());
            detailPhoneNumber.setText(neighbour.getPhoneNumber());
            detailWebSite.setText(neighbour.getWebSite());
            detailAboutMe.setText(neighbour.getAboutMe());
            fabFavorite.setImageResource(neighbour.getIsFavorite() ? R.drawable.ic_star_favorite : R.drawable.ic_star_border_black);
        }
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
    }
}