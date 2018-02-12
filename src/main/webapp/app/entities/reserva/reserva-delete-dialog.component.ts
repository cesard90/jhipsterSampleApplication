import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Reserva } from './reserva.model';
import { ReservaPopupService } from './reserva-popup.service';
import { ReservaService } from './reserva.service';

@Component({
    selector: 'jhi-reserva-delete-dialog',
    templateUrl: './reserva-delete-dialog.component.html'
})
export class ReservaDeleteDialogComponent {

    reserva: Reserva;

    constructor(
        private reservaService: ReservaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reservaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reservaListModification',
                content: 'Deleted an reserva'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reserva-delete-popup',
    template: ''
})
export class ReservaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reservaPopupService: ReservaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reservaPopupService
                .open(ReservaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
