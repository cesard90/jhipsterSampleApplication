import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Reserva } from './reserva.model';
import { ReservaPopupService } from './reserva-popup.service';
import { ReservaService } from './reserva.service';
import { TipoHabitacion, TipoHabitacionService } from '../tipo-habitacion';
import { Habitacion, HabitacionService } from '../habitacion';

@Component({
    selector: 'jhi-reserva-dialog',
    templateUrl: './reserva-dialog.component.html'
})
export class ReservaDialogComponent implements OnInit {

    reserva: Reserva;
    isSaving: boolean;

    tipohabitacions: TipoHabitacion[];

    habitacions: Habitacion[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private reservaService: ReservaService,
        private tipoHabitacionService: TipoHabitacionService,
        private habitacionService: HabitacionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tipoHabitacionService.query()
            .subscribe((res: HttpResponse<TipoHabitacion[]>) => { this.tipohabitacions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.habitacionService.query()
            .subscribe((res: HttpResponse<Habitacion[]>) => { this.habitacions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reserva.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reservaService.update(this.reserva));
        } else {
            this.subscribeToSaveResponse(
                this.reservaService.create(this.reserva));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Reserva>>) {
        result.subscribe((res: HttpResponse<Reserva>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Reserva) {
        this.eventManager.broadcast({ name: 'reservaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTipoHabitacionById(index: number, item: TipoHabitacion) {
        return item.id;
    }

    trackHabitacionById(index: number, item: Habitacion) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-reserva-popup',
    template: ''
})
export class ReservaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reservaPopupService: ReservaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reservaPopupService
                    .open(ReservaDialogComponent as Component, params['id']);
            } else {
                this.reservaPopupService
                    .open(ReservaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
