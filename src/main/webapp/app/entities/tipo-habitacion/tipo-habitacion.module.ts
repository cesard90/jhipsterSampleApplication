import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    TipoHabitacionService,
    TipoHabitacionPopupService,
    TipoHabitacionComponent,
    TipoHabitacionDetailComponent,
    TipoHabitacionDialogComponent,
    TipoHabitacionPopupComponent,
    TipoHabitacionDeletePopupComponent,
    TipoHabitacionDeleteDialogComponent,
    tipoHabitacionRoute,
    tipoHabitacionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tipoHabitacionRoute,
    ...tipoHabitacionPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TipoHabitacionComponent,
        TipoHabitacionDetailComponent,
        TipoHabitacionDialogComponent,
        TipoHabitacionDeleteDialogComponent,
        TipoHabitacionPopupComponent,
        TipoHabitacionDeletePopupComponent,
    ],
    entryComponents: [
        TipoHabitacionComponent,
        TipoHabitacionDialogComponent,
        TipoHabitacionPopupComponent,
        TipoHabitacionDeleteDialogComponent,
        TipoHabitacionDeletePopupComponent,
    ],
    providers: [
        TipoHabitacionService,
        TipoHabitacionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTipoHabitacionModule {}
