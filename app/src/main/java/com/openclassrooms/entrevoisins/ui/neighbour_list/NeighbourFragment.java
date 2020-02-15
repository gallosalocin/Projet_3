package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.openclassrooms.entrevoisins.ui.neighbour_details.DetailsNeighbourActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class NeighbourFragment extends Fragment implements MyNeighbourRecyclerViewAdapter.OnItemListener {

    private NeighbourApiService apiService;
    private List<Neighbour> neighbours;
    private List<Neighbour> favorites;
    private RecyclerView recyclerView;
    private int position;

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
        apiService = DI.getNeighbourApiService();

        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    public void initSelection() {
        switch (position) {
            case 0:
                recyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(neighbours, this));
                break;
            case 1:
                recyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(favorites, this));
                break;
            default:
                break;
        }
    }

    public void isNeighbourFavorite() {
        favorites = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getIsFavorite()) {
                favorites.add(neighbour);
            }
        }
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        neighbours = apiService.getNeighbours();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        initList();
        isNeighbourFavorite();
        initSelection();
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
        apiService.deleteNeighbour(event.neighbour);
        initList();
        isNeighbourFavorite();
        initSelection();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailsNeighbourActivity.class);
        Neighbour neighbour = (this.position == 0) ? neighbours.get(position) : favorites.get(position);
        intent.putExtra("Neighbour", neighbour);
        startActivity(intent);
    }
}

