import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Habitacion } from './habitacion.model';
import { HabitacionPopupService } from './habitacion-popup.service';
import { HabitacionService } from './habitacion.service';

@Component({
    selector: 'jhi-habitacion-delete-dialog',
    templateUrl: './habitacion-delete-dialog.component.html'
})
export class HabitacionDeleteDialogComponent {

    habitacion: Habitacion;

    constructor(
        private habitacionService: HabitacionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.habitacionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'habitacionListModification',
                content: 'Deleted an habitacion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-habitacion-delete-popup',
    template: ''
})
export class HabitacionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private habitacionPopupService: HabitacionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.habitacionPopupService
                .open(HabitacionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
