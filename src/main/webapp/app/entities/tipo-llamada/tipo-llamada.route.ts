import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TipoLlamadaComponent } from './tipo-llamada.component';
import { TipoLlamadaDetailComponent } from './tipo-llamada-detail.component';
import { TipoLlamadaPopupComponent } from './tipo-llamada-dialog.component';
import { TipoLlamadaDeletePopupComponent } from './tipo-llamada-delete-dialog.component';

export const tipoLlamadaRoute: Routes = [
    {
        path: 'tipo-llamada',
        component: TipoLlamadaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoLlamada.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tipo-llamada/:id',
        component: TipoLlamadaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoLlamada.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoLlamadaPopupRoute: Routes = [
    {
        path: 'tipo-llamada-new',
        component: TipoLlamadaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoLlamada.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-llamada/:id/edit',
        component: TipoLlamadaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoLlamada.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-llamada/:id/delete',
        component: TipoLlamadaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoLlamada.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
