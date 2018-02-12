package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Habitacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Habitacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    @Query("select distinct habitacion from Habitacion habitacion left join fetch habitacion.reservas")
    List<Habitacion> findAllWithEagerRelationships();

    @Query("select habitacion from Habitacion habitacion left join fetch habitacion.reservas where habitacion.id =:id")
    Habitacion findOneWithEagerRelationships(@Param("id") Long id);

}
