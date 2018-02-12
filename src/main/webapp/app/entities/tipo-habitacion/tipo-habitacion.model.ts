import { BaseEntity } from './../../shared';

export class TipoHabitacion implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public precio?: number,
    ) {
    }
}
