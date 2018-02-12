import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    HabitacionService,
    HabitacionPopupService,
    HabitacionComponent,
    HabitacionDetailComponent,
    HabitacionDialogComponent,
    HabitacionPopupComponent,
    HabitacionDeletePopupComponent,
    HabitacionDeleteDialogComponent,
    habitacionRoute,
    habitacionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...habitacionRoute,
    ...habitacionPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HabitacionComponent,
        HabitacionDetailComponent,
        HabitacionDialogComponent,
        HabitacionDeleteDialogComponent,
        HabitacionPopupComponent,
        HabitacionDeletePopupComponent,
    ],
    entryComponents: [
        HabitacionComponent,
        HabitacionDialogComponent,
        HabitacionPopupComponent,
        HabitacionDeleteDialogComponent,
        HabitacionDeletePopupComponent,
    ],
    providers: [
        HabitacionService,
        HabitacionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationHabitacionModule {}
