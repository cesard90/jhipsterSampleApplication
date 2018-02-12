import { BaseEntity } from './../../shared';

export class Reserva implements BaseEntity {
    constructor(
        public id?: number,
        public fechaInicio?: any,
        public fechaFin?: any,
        public fechaReserva?: any,
        public camaAdicional?: boolean,
        public precioVentaReserva?: number,
        public tipoHabitacion?: BaseEntity,
        public habitacions?: BaseEntity[],
    ) {
        this.camaAdicional = false;
    }
}
