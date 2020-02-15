package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class) public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void createNeighbourWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        service.getNeighbours().clear();
        service.createNeighbour(neighbour);
        assertEquals(1, service.getNeighbours().size());
        assertTrue(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.stream().map(Neighbour::getId).collect(Collectors.toList()).contains(neighbour.getId()));
        assertTrue(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.stream().map(Neighbour::getName).collect(Collectors.toList()).contains(neighbour.getName()));
        assertTrue(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.stream().map(Neighbour::getAvatarUrl).collect(Collectors.toList()).contains(neighbour.getAvatarUrl()));
        assertTrue(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.stream().map(Neighbour::getAddress).collect(Collectors.toList()).contains(neighbour.getAddress()));
        assertTrue(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.stream().map(Neighbour::getPhoneNumber).collect(Collectors.toList()).contains(neighbour.getPhoneNumber()));
        assertTrue(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.stream().map(Neighbour::getAboutMe).collect(Collectors.toList()).contains(neighbour.getAboutMe()));
    }

    @Test
    public void addNeighbourAsFavoriteWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        service.addNeighbourAsFavorite(neighbour);
        neighbour.setIsFavorite(true);
        assertTrue(service.getNeighbours().get(0).getIsFavorite());
    }
}
