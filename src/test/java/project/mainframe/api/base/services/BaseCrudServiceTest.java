package project.mainframe.api.base.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import project.mainframe.api.base.BaseTestDummy.*;

public class BaseCrudServiceTest {

    /*
    * An instance of the dummy service
    */
    private DummyService dummyService;

    /*
    * Dummy entity
    */
    private DummyEntity dummyEntity1;

    @BeforeEach
    public void beforeEach() {
        dummyService = DummyService.create();
        dummyEntity1 = new DummyEntity(1L, "Dummy 1");
    }

    /*
    * Test the findAll method
    */
    @Test
    public void testFindAll() {
        Mockito
        .when(dummyService.getDummyRepository().findAll())
        .thenReturn(List.of(dummyEntity1));

        List<DummyDTO> dummyResponses = dummyService.findAll();

        assertEquals(1, dummyResponses.size());
        assertEquals(dummyEntity1.getId(), dummyResponses.get(0).getId());
    }

    /*
    * Test the findById method
    */
    @Test
    public void testFindById() {
        Mockito
        .when(dummyService.getDummyRepository().findById(1L))
        .thenReturn(Optional.of(dummyEntity1));

        DummyDTO dummyResponse = dummyService.findById(1L);

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
            dummyService.getDummyRepository().save(Mockito.any(DummyEntity.class))
        )
        .thenReturn(dummyEntity1);

        DummyDTO dummyResponse = dummyService.create(dummyRequest);

        assertEquals(dummyEntity1.getId(), dummyResponse.getId());
    }

    /*
    * Test the update method
    */
    @Test
    public void testUpdate() {
        DummyDTO dummyRequest = new DummyDTO(dummyEntity1);

        Mockito
        .when(dummyService.getDummyRepository().findById(1L))
        .thenReturn(Optional.of(dummyEntity1));
        Mockito
        .when(
            dummyService.getDummyRepository().save(Mockito.any(DummyEntity.class))
        )
        .thenReturn(dummyEntity1);

        DummyDTO dummyResponse = dummyService.update(
        dummyRequest.getId(),
        dummyRequest
        );

        assertEquals(dummyEntity1.getId(), dummyResponse.getId());
    }

    /*
    * Test the delete method
    */
    @Test
    public void testDelete() {
        Mockito
        .when(dummyService.getDummyRepository().findById(1L))
        .thenReturn(Optional.of(dummyEntity1));

        dummyService.getDummyRepository().delete(dummyEntity1);

        Mockito
        .verify(dummyService.getDummyRepository(), Mockito.times(1))
        .delete(dummyEntity1);

        boolean deleted = dummyService.delete(1L);

        assertEquals(true, deleted);
    }
}
