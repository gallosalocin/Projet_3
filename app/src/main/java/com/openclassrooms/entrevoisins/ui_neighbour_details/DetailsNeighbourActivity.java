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
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourFragment;

import java.util.List;

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

    boolean isPressed = false;
    private NeighbourFragment mNeighbourFragment;
    private List<Neighbour> mFavorites;
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    DummyNeighbourGenerator mDummyNeighbourGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);
        ButterKnife.bind(this);

        sendExtras();
        configToolbar();
        myFabFavoriteButton();

    }

    public void myFabFavoriteButton() {

        fabFavorite.setOnClickListener(view -> {

            if (isPressed) {
                fabFavorite.setImageResource(R.drawable.ic_star_border_black);
                mApiService.removeFavorite();
            } else {
                fabFavorite.setImageResource(R.drawable.ic_star_favorite);
                mApiService.addFavorite();

            }
            isPressed = !isPressed;

        });
    }

    public void sendExtras() {
        if (getIntent().hasExtra("NeighbourDetail")) {

            Neighbour neighbour = getIntent().getParcelableExtra("NeighbourDetail");

            String avatar = neighbour.getAvatarUrl();
            String name = neighbour.getName();
            String address = neighbour.getAddress();
            String phone = neighbour.getPhoneNumber();
            String webSite = neighbour.getWebSite();
            String aboutMe = neighbour.getAboutMe();

            Glide.with(this).load(avatar).into(photo);
            titleName.setText(name);
            detailName.setText(name);
            detailAddress.setText(address);
            detailPhoneNumber.setText(phone);
            detailWebSite.setText(webSite);
            detailAboutMe.setText(aboutMe);

        }
        if (getIntent().hasExtra("FavoriteDetail")) {

            Neighbour neighbour = getIntent().getParcelableExtra("FavoriteDetail");

            String avatar = neighbour.getAvatarUrl();
            String name = neighbour.getName();
            String address = neighbour.getAddress();
            String phone = neighbour.getPhoneNumber();
            String webSite = neighbour.getWebSite();
            String aboutMe = neighbour.getAboutMe();

            Glide.with(this).load(avatar).into(photo);
            titleName.setText(name);
            detailName.setText(name);
            detailAddress.setText(address);
            detailPhoneNumber.setText(phone);
            detailWebSite.setText(webSite);
            detailAboutMe.setText(aboutMe);
        }
    }

    private void configToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_details);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}