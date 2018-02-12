import { BaseEntity } from './../../shared';

export class Habitacion implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public piso?: string,
        public ocupada?: boolean,
        public codigo?: string,
        public hotel?: BaseEntity,
        public categoria?: BaseEntity,
        public llamada?: BaseEntity,
        public tipoHabitacion?: BaseEntity,
        public reservas?: BaseEntity[],
    ) {
        this.ocupada = false;
    }
}
