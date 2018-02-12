import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Habitacion } from './habitacion.model';
import { HabitacionPopupService } from './habitacion-popup.service';
import { HabitacionService } from './habitacion.service';
import { Hotel, HotelService } from '../hotel';
import { Categoria, CategoriaService } from '../categoria';
import { Llamada, LlamadaService } from '../llamada';
import { TipoHabitacion, TipoHabitacionService } from '../tipo-habitacion';
import { Reserva, ReservaService } from '../reserva';

@Component({
    selector: 'jhi-habitacion-dialog',
    templateUrl: './habitacion-dialog.component.html'
})
export class HabitacionDialogComponent implements OnInit {

    habitacion: Habitacion;
    isSaving: boolean;

    hotels: Hotel[];

    categorias: Categoria[];

    llamadas: Llamada[];

    tipohabitacions: TipoHabitacion[];

    reservas: Reserva[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private habitacionService: HabitacionService,
        private hotelService: HotelService,
        private categoriaService: CategoriaService,
        private llamadaService: LlamadaService,
        private tipoHabitacionService: TipoHabitacionService,
        private reservaService: ReservaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.hotelService.query()
            .subscribe((res: HttpResponse<Hotel[]>) => { this.hotels = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.categoriaService.query()
            .subscribe((res: HttpResponse<Categoria[]>) => { this.categorias = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.llamadaService.query()
            .subscribe((res: HttpResponse<Llamada[]>) => { this.llamadas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tipoHabitacionService.query()
            .subscribe((res: HttpResponse<TipoHabitacion[]>) => { this.tipohabitacions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.reservaService.query()
            .subscribe((res: HttpResponse<Reserva[]>) => { this.reservas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.habitacion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.habitacionService.update(this.habitacion));
        } else {
            this.subscribeToSaveResponse(
                this.habitacionService.create(this.habitacion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Habitacion>>) {
        result.subscribe((res: HttpResponse<Habitacion>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Habitacion) {
        this.eventManager.broadcast({ name: 'habitacionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackHotelById(index: number, item: Hotel) {
        return item.id;
    }

    trackCategoriaById(index: number, item: Categoria) {
        return item.id;
    }

    trackLlamadaById(index: number, item: Llamada) {
        return item.id;
    }

    trackTipoHabitacionById(index: number, item: TipoHabitacion) {
        return item.id;
    }

    trackReservaById(index: number, item: Reserva) {
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
    selector: 'jhi-habitacion-popup',
    template: ''
})
export class HabitacionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private habitacionPopupService: HabitacionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.habitacionPopupService
                    .open(HabitacionDialogComponent as Component, params['id']);
            } else {
                this.habitacionPopupService
                    .open(HabitacionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
