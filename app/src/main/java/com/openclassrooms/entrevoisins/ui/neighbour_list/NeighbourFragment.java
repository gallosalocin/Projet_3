package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui_neighbour_details.DetailsNeighbourActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class NeighbourFragment extends Fragment implements MyNeighbourRecyclerViewAdapter.OnItemListener {

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.tabItem)
    TabItem mTabItemMyNeighbours;
    @BindView(R.id.tabItem2)
    TabItem mTabItemFavorites;

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private List<Neighbour> mFavorites;
    private RecyclerView mRecyclerView;
    private int mPosition;


    /**
     * Create and return a new instance
     *
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(int position) {
        NeighbourFragment neighbourFragment = new NeighbourFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        neighbourFragment.setArguments(args);
        return neighbourFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();

        if (getArguments() != null) {
            mPosition = getArguments().getInt("position", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        initList();
        isNeighbourFavorite();
        initSelection();

        return view;
    }

    public void initSelection() {
        switch (mPosition) {
            case 0:
                mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours, this));
                break;
            case 1:
                mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mFavorites, this));
                break;
            default:
                break;
        }
    }

    public void isNeighbourFavorite() {
        mFavorites = new ArrayList<>();
        for (Neighbour neighbour : mNeighbours) {
            if (neighbour.isFavorite()) {
                mFavorites.add(neighbour);
            }
        }
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        mNeighbours = mApiService.getNeighbours();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        isNeighbourFavorite();
        switch (mPosition) {
            case 0:
                mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours, this));
                break;
            case 1:
                mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mFavorites, this));
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailsNeighbourActivity.class);
        switch (mPosition) {
            case 0:
                intent.putExtra("NeighbourDetail", mNeighbours.get(position));
                break;
            case 1:
                intent.putExtra("FavoriteDetail", mFavorites.get(position));
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
