import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Reserva } from './reserva.model';
import { ReservaService } from './reserva.service';

@Injectable()
export class ReservaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private reservaService: ReservaService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.reservaService.find(id)
                    .subscribe((reservaResponse: HttpResponse<Reserva>) => {
                        const reserva: Reserva = reservaResponse.body;
                        reserva.fechaInicio = this.datePipe
                            .transform(reserva.fechaInicio, 'yyyy-MM-ddTHH:mm:ss');
                        reserva.fechaFin = this.datePipe
                            .transform(reserva.fechaFin, 'yyyy-MM-ddTHH:mm:ss');
                        reserva.fechaReserva = this.datePipe
                            .transform(reserva.fechaReserva, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.reservaModalRef(component, reserva);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.reservaModalRef(component, new Reserva());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    reservaModalRef(component: Component, reserva: Reserva): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reserva = reserva;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
