import { BaseEntity } from './../../shared';

export const enum Estrellas {
    'UNO',
    'DOS',
    'TRES',
    'CUATRO',
    'CINCO'
}

export class Hotel implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public estrellas?: Estrellas,
        public direccion?: string,
        public ciudad?: string,
        public telefono?: string,
        public habitacions?: BaseEntity[],
    ) {
    }
}
