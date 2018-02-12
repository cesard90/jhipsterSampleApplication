package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Llamada;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Llamada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LlamadaRepository extends JpaRepository<Llamada, Long> {

}
