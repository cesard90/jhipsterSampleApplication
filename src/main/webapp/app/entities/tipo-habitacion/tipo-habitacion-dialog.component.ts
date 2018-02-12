import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoHabitacion } from './tipo-habitacion.model';
import { TipoHabitacionPopupService } from './tipo-habitacion-popup.service';
import { TipoHabitacionService } from './tipo-habitacion.service';

@Component({
    selector: 'jhi-tipo-habitacion-dialog',
    templateUrl: './tipo-habitacion-dialog.component.html'
})
export class TipoHabitacionDialogComponent implements OnInit {

    tipoHabitacion: TipoHabitacion;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tipoHabitacionService: TipoHabitacionService,
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
        if (this.tipoHabitacion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipoHabitacionService.update(this.tipoHabitacion));
        } else {
            this.subscribeToSaveResponse(
                this.tipoHabitacionService.create(this.tipoHabitacion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TipoHabitacion>>) {
        result.subscribe((res: HttpResponse<TipoHabitacion>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TipoHabitacion) {
        this.eventManager.broadcast({ name: 'tipoHabitacionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-tipo-habitacion-popup',
    template: ''
})
export class TipoHabitacionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoHabitacionPopupService: TipoHabitacionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tipoHabitacionPopupService
                    .open(TipoHabitacionDialogComponent as Component, params['id']);
            } else {
                this.tipoHabitacionPopupService
                    .open(TipoHabitacionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
