import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TipoHabitacionComponent } from './tipo-habitacion.component';
import { TipoHabitacionDetailComponent } from './tipo-habitacion-detail.component';
import { TipoHabitacionPopupComponent } from './tipo-habitacion-dialog.component';
import { TipoHabitacionDeletePopupComponent } from './tipo-habitacion-delete-dialog.component';

export const tipoHabitacionRoute: Routes = [
    {
        path: 'tipo-habitacion',
        component: TipoHabitacionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoHabitacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tipo-habitacion/:id',
        component: TipoHabitacionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoHabitacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoHabitacionPopupRoute: Routes = [
    {
        path: 'tipo-habitacion-new',
        component: TipoHabitacionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoHabitacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-habitacion/:id/edit',
        component: TipoHabitacionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoHabitacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-habitacion/:id/delete',
        component: TipoHabitacionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tipoHabitacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
