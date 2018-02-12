import { BaseEntity } from './../../shared';

export class TipoLlamada implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public precio?: number,
    ) {
    }
}
