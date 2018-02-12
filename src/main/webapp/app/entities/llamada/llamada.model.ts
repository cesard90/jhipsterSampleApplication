import { BaseEntity } from './../../shared';

export class Llamada implements BaseEntity {
    constructor(
        public id?: number,
        public minutos?: number,
        public tipoLlamada?: BaseEntity,
    ) {
    }
}
