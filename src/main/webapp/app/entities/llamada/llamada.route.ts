import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LlamadaComponent } from './llamada.component';
import { LlamadaDetailComponent } from './llamada-detail.component';
import { LlamadaPopupComponent } from './llamada-dialog.component';
import { LlamadaDeletePopupComponent } from './llamada-delete-dialog.component';

export const llamadaRoute: Routes = [
    {
        path: 'llamada',
        component: LlamadaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.llamada.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'llamada/:id',
        component: LlamadaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.llamada.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const llamadaPopupRoute: Routes = [
    {
        path: 'llamada-new',
        component: LlamadaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.llamada.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'llamada/:id/edit',
        component: LlamadaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.llamada.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'llamada/:id/delete',
        component: LlamadaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.llamada.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
