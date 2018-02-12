package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Habitacion.
 */
@Entity
@Table(name = "habitacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "habitacion")
public class Habitacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "piso", nullable = false)
    private String piso;

    @Column(name = "ocupada")
    private Boolean ocupada;

    @Column(name = "codigo")
    private String codigo;

    @ManyToOne
    private Hotel hotel;

    @ManyToOne(optional = false)
    @NotNull
    private Categoria categoria;

    @ManyToOne(optional = false)
    @NotNull
    private Llamada llamada;

    @ManyToOne(optional = false)
    @NotNull
    private TipoHabitacion tipoHabitacion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "habitacion_reserva",
               joinColumns = @JoinColumn(name="habitacions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="reservas_id", referencedColumnName="id"))
    private Set<Reserva> reservas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Habitacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPiso() {
        return piso;
    }

    public Habitacion piso(String piso) {
        this.piso = piso;
        return this;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public Boolean isOcupada() {
        return ocupada;
    }

    public Habitacion ocupada(Boolean ocupada) {
        this.ocupada = ocupada;
        return this;
    }

    public void setOcupada(Boolean ocupada) {
        this.ocupada = ocupada;
    }

    public String getCodigo() {
        return codigo;
    }

    public Habitacion codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Habitacion hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Habitacion categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Llamada getLlamada() {
        return llamada;
    }

    public Habitacion llamada(Llamada llamada) {
        this.llamada = llamada;
        return this;
    }

    public void setLlamada(Llamada llamada) {
        this.llamada = llamada;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public Habitacion tipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
        return this;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public Habitacion reservas(Set<Reserva> reservas) {
        this.reservas = reservas;
        return this;
    }

    public Habitacion addReserva(Reserva reserva) {
        this.reservas.add(reserva);
        reserva.getHabitacions().add(this);
        return this;
    }

    public Habitacion removeReserva(Reserva reserva) {
        this.reservas.remove(reserva);
        reserva.getHabitacions().remove(this);
        return this;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
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
        Habitacion habitacion = (Habitacion) o;
        if (habitacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), habitacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Habitacion{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", piso='" + getPiso() + "'" +
            ", ocupada='" + isOcupada() + "'" +
            ", codigo='" + getCodigo() + "'" +
            "}";
    }
}
