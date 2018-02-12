import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    ReservaService,
    ReservaPopupService,
    ReservaComponent,
    ReservaDetailComponent,
    ReservaDialogComponent,
    ReservaPopupComponent,
    ReservaDeletePopupComponent,
    ReservaDeleteDialogComponent,
    reservaRoute,
    reservaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reservaRoute,
    ...reservaPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ReservaComponent,
        ReservaDetailComponent,
        ReservaDialogComponent,
        ReservaDeleteDialogComponent,
        ReservaPopupComponent,
        ReservaDeletePopupComponent,
    ],
    entryComponents: [
        ReservaComponent,
        ReservaDialogComponent,
        ReservaPopupComponent,
        ReservaDeleteDialogComponent,
        ReservaDeletePopupComponent,
    ],
    providers: [
        ReservaService,
        ReservaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationReservaModule {}
