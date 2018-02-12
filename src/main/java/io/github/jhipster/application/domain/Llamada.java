package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Llamada.
 */
@Entity
@Table(name = "llamada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "llamada")
public class Llamada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "minutos")
    private Double minutos;

    @ManyToOne(optional = false)
    @NotNull
    private TipoLlamada tipoLlamada;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinutos() {
        return minutos;
    }

    public Llamada minutos(Double minutos) {
        this.minutos = minutos;
        return this;
    }

    public void setMinutos(Double minutos) {
        this.minutos = minutos;
    }

    public TipoLlamada getTipoLlamada() {
        return tipoLlamada;
    }

    public Llamada tipoLlamada(TipoLlamada tipoLlamada) {
        this.tipoLlamada = tipoLlamada;
        return this;
    }

    public void setTipoLlamada(TipoLlamada tipoLlamada) {
        this.tipoLlamada = tipoLlamada;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Llamada llamada = (Llamada) o;
        if (llamada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), llamada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Llamada{" +
            "id=" + getId() +
            ", minutos=" + getMinutos() +
            "}";
    }
}
