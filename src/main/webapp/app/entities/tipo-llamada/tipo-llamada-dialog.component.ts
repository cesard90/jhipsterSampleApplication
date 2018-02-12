import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoLlamada } from './tipo-llamada.model';
import { TipoLlamadaPopupService } from './tipo-llamada-popup.service';
import { TipoLlamadaService } from './tipo-llamada.service';

@Component({
    selector: 'jhi-tipo-llamada-dialog',
    templateUrl: './tipo-llamada-dialog.component.html'
})
export class TipoLlamadaDialogComponent implements OnInit {

    tipoLlamada: TipoLlamada;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tipoLlamadaService: TipoLlamadaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tipoLlamada.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipoLlamadaService.update(this.tipoLlamada));
        } else {
            this.subscribeToSaveResponse(
                this.tipoLlamadaService.create(this.tipoLlamada));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TipoLlamada>>) {
        result.subscribe((res: HttpResponse<TipoLlamada>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TipoLlamada) {
        this.eventManager.broadcast({ name: 'tipoLlamadaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-tipo-llamada-popup',
    template: ''
})
export class TipoLlamadaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoLlamadaPopupService: TipoLlamadaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tipoLlamadaPopupService
                    .open(TipoLlamadaDialogComponent as Component, params['id']);
            } else {
                this.tipoLlamadaPopupService
                    .open(TipoLlamadaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
