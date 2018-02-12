import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Habitacion } from './habitacion.model';
import { HabitacionService } from './habitacion.service';

@Component({
    selector: 'jhi-habitacion-detail',
    templateUrl: './habitacion-detail.component.html'
})
export class HabitacionDetailComponent implements OnInit, OnDestroy {

    habitacion: Habitacion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private habitacionService: HabitacionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHabitacions();
    }

    load(id) {
        this.habitacionService.find(id)
            .subscribe((habitacionResponse: HttpResponse<Habitacion>) => {
                this.habitacion = habitacionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHabitacions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'habitacionListModification',
            (response) => this.load(this.habitacion.id)
        );
    }
}
