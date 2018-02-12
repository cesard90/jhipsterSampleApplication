import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TipoHabitacion } from './tipo-habitacion.model';
import { TipoHabitacionService } from './tipo-habitacion.service';

@Injectable()
export class TipoHabitacionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tipoHabitacionService: TipoHabitacionService

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
                this.tipoHabitacionService.find(id)
                    .subscribe((tipoHabitacionResponse: HttpResponse<TipoHabitacion>) => {
                        const tipoHabitacion: TipoHabitacion = tipoHabitacionResponse.body;
                        this.ngbModalRef = this.tipoHabitacionModalRef(component, tipoHabitacion);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tipoHabitacionModalRef(component, new TipoHabitacion());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tipoHabitacionModalRef(component: Component, tipoHabitacion: TipoHabitacion): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tipoHabitacion = tipoHabitacion;
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
