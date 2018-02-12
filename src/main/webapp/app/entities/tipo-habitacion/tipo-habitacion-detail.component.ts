import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TipoHabitacion } from './tipo-habitacion.model';
import { TipoHabitacionService } from './tipo-habitacion.service';

@Component({
    selector: 'jhi-tipo-habitacion-detail',
    templateUrl: './tipo-habitacion-detail.component.html'
})
export class TipoHabitacionDetailComponent implements OnInit, OnDestroy {

    tipoHabitacion: TipoHabitacion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tipoHabitacionService: TipoHabitacionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTipoHabitacions();
    }

    load(id) {
        this.tipoHabitacionService.find(id)
            .subscribe((tipoHabitacionResponse: HttpResponse<TipoHabitacion>) => {
                this.tipoHabitacion = tipoHabitacionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTipoHabitacions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipoHabitacionListModification',
            (response) => this.load(this.tipoHabitacion.id)
        );
    }
}
