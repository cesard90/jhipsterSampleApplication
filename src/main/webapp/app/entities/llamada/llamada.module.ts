import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    LlamadaService,
    LlamadaPopupService,
    LlamadaComponent,
    LlamadaDetailComponent,
    LlamadaDialogComponent,
    LlamadaPopupComponent,
    LlamadaDeletePopupComponent,
    LlamadaDeleteDialogComponent,
    llamadaRoute,
    llamadaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...llamadaRoute,
    ...llamadaPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LlamadaComponent,
        LlamadaDetailComponent,
        LlamadaDialogComponent,
        LlamadaDeleteDialogComponent,
        LlamadaPopupComponent,
        LlamadaDeletePopupComponent,
    ],
    entryComponents: [
        LlamadaComponent,
        LlamadaDialogComponent,
        LlamadaPopupComponent,
        LlamadaDeleteDialogComponent,
        LlamadaDeletePopupComponent,
    ],
    providers: [
        LlamadaService,
        LlamadaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationLlamadaModule {}
