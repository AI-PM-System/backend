package project.mainframe.api.base;

import org.mockito.Mockito;
import org.springframework.data.jpa.repository.JpaRepository;

import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;

public class BaseTestDummy {
    /*
     * A dummy entity for testing purposes
     */
    @Getter
    @Setter
    public static class DummyEntity {

        private Long id;

        private String name;

        public DummyEntity(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    
    /*
     * A dummy DTO representing both request and response
     */
    @Getter
    @Setter
    public static class DummyDTO {
        private Long id;
        private String name;

        public DummyDTO(DummyEntity entity) {
            this.id = entity.getId();
            this.name = entity.getName();
        }
    }

    public interface DummyRepository extends JpaRepository<DummyEntity, Long> {}

    /*
     * A dummy service for testing purposes
     */
    // @Service
    public static class DummyService extends BaseCrudService<DummyDTO, DummyDTO, DummyEntity, Long> {
        public DummyService(JpaRepository<DummyEntity, Long> jpaRepository) {
            super(jpaRepository);
        }

        /*
         * Maps a dummy entity to a dummy response
         * 
         * @param entity The dummy entity to map
         * @return DummyResponse
         */
        @Override
        public DummyDTO mapToResponse(DummyEntity entity) {
            return new DummyDTO(entity);
        }

        /*
         * Maps a dummy request to a dummy entity
         * 
         * @param request The dummy request to map
         * @return DummyEntity
         */
        @Override
        public DummyEntity mapToEntity(DummyDTO request) {
            return new DummyEntity(request.getId(), request.getName());
        }

        /*
         * Returns the dummy repository
         */
        public JpaRepository<DummyEntity, Long> getDummyRepository() {
            return jpaRepository;
        }

        public static DummyService create() {
            return new DummyService(Mockito.mock(DummyRepository.class));
        }
    }

    public static class DummyController extends BaseCrudController<DummyDTO, DummyDTO, DummyEntity, Long> {
        public DummyController(DummyService dummyService) {
            super(dummyService);
        }

        public DummyService getDummyService() {
            return (DummyService) baseCrudService;
        }

        public static DummyController create() {
            return new DummyController(DummyService.create());
        }
    } 
}
