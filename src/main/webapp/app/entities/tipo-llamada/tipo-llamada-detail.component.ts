import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TipoLlamada } from './tipo-llamada.model';
import { TipoLlamadaService } from './tipo-llamada.service';

@Component({
    selector: 'jhi-tipo-llamada-detail',
    templateUrl: './tipo-llamada-detail.component.html'
})
export class TipoLlamadaDetailComponent implements OnInit, OnDestroy {

    tipoLlamada: TipoLlamada;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tipoLlamadaService: TipoLlamadaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTipoLlamadas();
    }

    load(id) {
        this.tipoLlamadaService.find(id)
            .subscribe((tipoLlamadaResponse: HttpResponse<TipoLlamada>) => {
                this.tipoLlamada = tipoLlamadaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTipoLlamadas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipoLlamadaListModification',
            (response) => this.load(this.tipoLlamada.id)
        );
    }
}
