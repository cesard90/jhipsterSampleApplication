package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Reserva.
 */
@Entity
@Table(name = "reserva")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reserva")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private ZonedDateTime fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private ZonedDateTime fechaFin;

    @Column(name = "fecha_reserva")
    private ZonedDateTime fechaReserva;

    @Column(name = "cama_adicional")
    private Boolean camaAdicional;

    @Column(name = "precio_venta_reserva")
    private Double precioVentaReserva;

    @ManyToOne(optional = false)
    @NotNull
    private TipoHabitacion tipoHabitacion;

    @ManyToMany(mappedBy = "reservas")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Habitacion> habitacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaInicio() {
        return fechaInicio;
    }

    public Reserva fechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public ZonedDateTime getFechaFin() {
        return fechaFin;
    }

    public Reserva fechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ZonedDateTime getFechaReserva() {
        return fechaReserva;
    }

    public Reserva fechaReserva(ZonedDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
        return this;
    }

    public void setFechaReserva(ZonedDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Boolean isCamaAdicional() {
        return camaAdicional;
    }

    public Reserva camaAdicional(Boolean camaAdicional) {
        this.camaAdicional = camaAdicional;
        return this;
    }

    public void setCamaAdicional(Boolean camaAdicional) {
        this.camaAdicional = camaAdicional;
    }

    public Double getPrecioVentaReserva() {
        return precioVentaReserva;
    }

    public Reserva precioVentaReserva(Double precioVentaReserva) {
        this.precioVentaReserva = precioVentaReserva;
        return this;
    }

    public void setPrecioVentaReserva(Double precioVentaReserva) {
        this.precioVentaReserva = precioVentaReserva;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public Reserva tipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
        return this;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public Set<Habitacion> getHabitacions() {
        return habitacions;
    }

    public Reserva habitacions(Set<Habitacion> habitacions) {
        this.habitacions = habitacions;
        return this;
    }

    public Reserva addHabitacion(Habitacion habitacion) {
        this.habitacions.add(habitacion);
        habitacion.getReservas().add(this);
        return this;
    }

    public Reserva removeHabitacion(Habitacion habitacion) {
        this.habitacions.remove(habitacion);
        habitacion.getReservas().remove(this);
        return this;
    }

    public void setHabitacions(Set<Habitacion> habitacions) {
        this.habitacions = habitacions;
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
        Reserva reserva = (Reserva) o;
        if (reserva.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reserva.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reserva{" +
            "id=" + getId() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", fechaReserva='" + getFechaReserva() + "'" +
            ", camaAdicional='" + isCamaAdicional() + "'" +
            ", precioVentaReserva=" + getPrecioVentaReserva() +
            "}";
    }
}
