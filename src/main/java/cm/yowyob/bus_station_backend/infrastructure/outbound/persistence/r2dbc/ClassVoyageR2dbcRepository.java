package cm.yowyob.bus_station_backend.infrastructure.outbound.persistence.r2dbc;
import cm.yowyob.bus_station_backend.infrastructure.outbound.persistence.entity.ClassVoyageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface ClassVoyageR2dbcRepository extends R2dbcRepository<ClassVoyageEntity, UUID> {
    @Query("""
        SELECT * FROM class_voyage
        LIMIT :#{#pageable.pageSize}
        OFFSET :#{#pageable.offset}
    """)
    Flux<ClassVoyageEntity> findAllPaged(Pageable pageable);

    @Query("SELECT * FROM class_voyage")
    Flux<ClassVoyageEntity> findByIdAgenceVoyage(UUID agenceId);
}