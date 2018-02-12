import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoHabitacion } from './tipo-habitacion.model';
import { TipoHabitacionPopupService } from './tipo-habitacion-popup.service';
import { TipoHabitacionService } from './tipo-habitacion.service';

@Component({
    selector: 'jhi-tipo-habitacion-delete-dialog',
    templateUrl: './tipo-habitacion-delete-dialog.component.html'
})
export class TipoHabitacionDeleteDialogComponent {

    tipoHabitacion: TipoHabitacion;

    constructor(
        private tipoHabitacionService: TipoHabitacionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoHabitacionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipoHabitacionListModification',
                content: 'Deleted an tipoHabitacion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-habitacion-delete-popup',
    template: ''
})
export class TipoHabitacionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoHabitacionPopupService: TipoHabitacionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tipoHabitacionPopupService
                .open(TipoHabitacionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
