import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    TipoLlamadaService,
    TipoLlamadaPopupService,
    TipoLlamadaComponent,
    TipoLlamadaDetailComponent,
    TipoLlamadaDialogComponent,
    TipoLlamadaPopupComponent,
    TipoLlamadaDeletePopupComponent,
    TipoLlamadaDeleteDialogComponent,
    tipoLlamadaRoute,
    tipoLlamadaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tipoLlamadaRoute,
    ...tipoLlamadaPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TipoLlamadaComponent,
        TipoLlamadaDetailComponent,
        TipoLlamadaDialogComponent,
        TipoLlamadaDeleteDialogComponent,
        TipoLlamadaPopupComponent,
        TipoLlamadaDeletePopupComponent,
    ],
    entryComponents: [
        TipoLlamadaComponent,
        TipoLlamadaDialogComponent,
        TipoLlamadaPopupComponent,
        TipoLlamadaDeleteDialogComponent,
        TipoLlamadaDeletePopupComponent,
    ],
    providers: [
        TipoLlamadaService,
        TipoLlamadaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTipoLlamadaModule {}
