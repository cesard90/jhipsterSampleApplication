import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ReservaComponent } from './reserva.component';
import { ReservaDetailComponent } from './reserva-detail.component';
import { ReservaPopupComponent } from './reserva-dialog.component';
import { ReservaDeletePopupComponent } from './reserva-delete-dialog.component';

export const reservaRoute: Routes = [
    {
        path: 'reserva',
        component: ReservaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.reserva.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reserva/:id',
        component: ReservaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.reserva.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reservaPopupRoute: Routes = [
    {
        path: 'reserva-new',
        component: ReservaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.reserva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reserva/:id/edit',
        component: ReservaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.reserva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reserva/:id/delete',
        component: ReservaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.reserva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
