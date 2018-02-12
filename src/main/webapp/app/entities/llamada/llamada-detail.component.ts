import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Llamada } from './llamada.model';
import { LlamadaService } from './llamada.service';

@Component({
    selector: 'jhi-llamada-detail',
    templateUrl: './llamada-detail.component.html'
})
export class LlamadaDetailComponent implements OnInit, OnDestroy {

    llamada: Llamada;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private llamadaService: LlamadaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLlamadas();
    }

    load(id) {
        this.llamadaService.find(id)
            .subscribe((llamadaResponse: HttpResponse<Llamada>) => {
                this.llamada = llamadaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLlamadas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'llamadaListModification',
            (response) => this.load(this.llamada.id)
        );
    }
}
