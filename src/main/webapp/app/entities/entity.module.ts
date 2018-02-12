import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationHotelModule } from './hotel/hotel.module';
import { JhipsterSampleApplicationHabitacionModule } from './habitacion/habitacion.module';
import { JhipsterSampleApplicationTipoHabitacionModule } from './tipo-habitacion/tipo-habitacion.module';
import { JhipsterSampleApplicationReservaModule } from './reserva/reserva.module';
import { JhipsterSampleApplicationLlamadaModule } from './llamada/llamada.module';
import { JhipsterSampleApplicationTipoLlamadaModule } from './tipo-llamada/tipo-llamada.module';
import { JhipsterSampleApplicationCategoriaModule } from './categoria/categoria.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleApplicationHotelModule,
        JhipsterSampleApplicationHabitacionModule,
        JhipsterSampleApplicationTipoHabitacionModule,
        JhipsterSampleApplicationReservaModule,
        JhipsterSampleApplicationLlamadaModule,
        JhipsterSampleApplicationTipoLlamadaModule,
        JhipsterSampleApplicationCategoriaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
