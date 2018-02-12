package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TipoHabitacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoHabitacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoHabitacionRepository extends JpaRepository<TipoHabitacion, Long> {

}
