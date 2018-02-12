import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HabitacionComponent } from './habitacion.component';
import { HabitacionDetailComponent } from './habitacion-detail.component';
import { HabitacionPopupComponent } from './habitacion-dialog.component';
import { HabitacionDeletePopupComponent } from './habitacion-delete-dialog.component';

export const habitacionRoute: Routes = [
    {
        path: 'habitacion',
        component: HabitacionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.habitacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'habitacion/:id',
        component: HabitacionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.habitacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const habitacionPopupRoute: Routes = [
    {
        path: 'habitacion-new',
        component: HabitacionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.habitacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'habitacion/:id/edit',
        component: HabitacionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.habitacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'habitacion/:id/delete',
        component: HabitacionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.habitacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
