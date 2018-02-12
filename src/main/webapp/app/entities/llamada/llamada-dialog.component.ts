import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Llamada } from './llamada.model';
import { LlamadaPopupService } from './llamada-popup.service';
import { LlamadaService } from './llamada.service';
import { TipoLlamada, TipoLlamadaService } from '../tipo-llamada';

@Component({
    selector: 'jhi-llamada-dialog',
    templateUrl: './llamada-dialog.component.html'
})
export class LlamadaDialogComponent implements OnInit {

    llamada: Llamada;
    isSaving: boolean;

    tipollamadas: TipoLlamada[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private llamadaService: LlamadaService,
        private tipoLlamadaService: TipoLlamadaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tipoLlamadaService.query()
            .subscribe((res: HttpResponse<TipoLlamada[]>) => { this.tipollamadas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.llamada.id !== undefined) {
            this.subscribeToSaveResponse(
                this.llamadaService.update(this.llamada));
        } else {
            this.subscribeToSaveResponse(
                this.llamadaService.create(this.llamada));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Llamada>>) {
        result.subscribe((res: HttpResponse<Llamada>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Llamada) {
        this.eventManager.broadcast({ name: 'llamadaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTipoLlamadaById(index: number, item: TipoLlamada) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-llamada-popup',
    template: ''
})
export class LlamadaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private llamadaPopupService: LlamadaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.llamadaPopupService
                    .open(LlamadaDialogComponent as Component, params['id']);
            } else {
                this.llamadaPopupService
                    .open(LlamadaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
