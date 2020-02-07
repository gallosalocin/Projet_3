package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

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
    public void addNeighbourAsFavorite(Neighbour neighbour) {
        /**
         * TODO
         * Récupérer le voisin dans la liste globale = Avoir sa position dans la liste
         */
        int position = neighbours.indexOf(neighbour);
        neighbours.get(position).setIsFavorite(!neighbour.getIsFavorite());
    }
}
