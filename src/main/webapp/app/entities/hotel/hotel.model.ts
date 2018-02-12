import { BaseEntity } from './../../shared';

export const enum Estrellas {
    '1',
    '2',
    '3',
    '4',
    '5'
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
