package project.mainframe.api.base.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import project.mainframe.api.base.BaseTestDummy.*;

public class BaseCrudControllerTest {

    /*
     * An instance of the dummy controller
     */
    private DummyController dummyController;

    /*
     * Dummy entity
     */
    private DummyEntity dummyEntity1;

    @BeforeEach
    public void beforeEach() {
        dummyController = DummyController.create();
        dummyEntity1 = new DummyEntity(1L, "Dummy 1");
    }

    /*
     * Test the findAll method
     */
    @Test
    public void testFindAll() {
        Mockito
        .when(dummyController.getDummyService().getDummyRepository().findAll())
        .thenReturn(List.of(dummyEntity1));

        List<DummyDTO> dummyResponses = dummyController.findAll();

        assertEquals(1, dummyResponses.size());
        assertEquals(dummyEntity1.getId(), dummyResponses.get(0).getId());
    }

    /*
     * Test the findById method
     */
    @Test
    public void testFindById() {
        Mockito
        .when(dummyController.getDummyService().getDummyRepository().findById(1L))
        .thenReturn(Optional.of(dummyEntity1));

        DummyDTO dummyResponse = dummyController.findById(1L);

        assertEquals(dummyEntity1.getId(), dummyResponse.getId());
    }

    /*
     * Test the create method
     */
    @Test
    public void testCreate() {
        DummyDTO dummyRequest = new DummyDTO(dummyEntity1);

        Mockito
        .when(
            dummyController.getDummyService().getDummyRepository().save(Mockito.any(DummyEntity.class))
        )
        .thenReturn(dummyEntity1);

        DummyDTO dummyResponse = dummyController.create(dummyRequest);

        assertEquals(dummyEntity1.getId(), dummyResponse.getId());
    }

    /*
     * Test the update method
     */
    @Test
    public void testUpdate() {
        DummyDTO dummyRequest = new DummyDTO(dummyEntity1);

        Mockito
        .when(dummyController.getDummyService().getDummyRepository().findById(1L))
        .thenReturn(Optional.of(dummyEntity1));

        Mockito
        .when(
            dummyController.getDummyService().getDummyRepository().save(Mockito.any(DummyEntity.class))
        )
        .thenReturn(dummyEntity1);
            
        DummyDTO dummyResponse = dummyController.update(1L, dummyRequest);

        assertEquals(dummyEntity1.getId(), dummyResponse.getId());
    }

    /*
     * Test the delete method
     */
    @Test
    public void testDelete() {
        Mockito
        .when(dummyController.getDummyService().getDummyRepository().findById(1L))
        .thenReturn(Optional.of(dummyEntity1));

        dummyController.delete(1L);

        Mockito.verify(dummyController.getDummyService().getDummyRepository(), Mockito.times(1)).delete(dummyEntity1);
    }
}
