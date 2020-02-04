package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    private List<Neighbour> favorite = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }


    @Override
    public void addFavorite() {
        for (Neighbour neighbour : neighbours) {
            if (neighbour.isFavorite()) {
                favorite.add(neighbour);
            }
        }
    }

    @Override
    public void removeFavorite() {
        for (Neighbour neighbour : neighbours) {
            if (!neighbour.isFavorite()) {
                favorite.add(neighbour);
            }
        }
    }

    @Override
    public List<Neighbour> getFavorite() {
        return favorite;
    }
}
