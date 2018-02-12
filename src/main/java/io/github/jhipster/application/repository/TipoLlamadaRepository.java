package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TipoLlamada;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoLlamada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoLlamadaRepository extends JpaRepository<TipoLlamada, Long> {

}
