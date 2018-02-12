import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Llamada } from './llamada.model';
import { LlamadaPopupService } from './llamada-popup.service';
import { LlamadaService } from './llamada.service';

@Component({
    selector: 'jhi-llamada-delete-dialog',
    templateUrl: './llamada-delete-dialog.component.html'
})
export class LlamadaDeleteDialogComponent {

    llamada: Llamada;

    constructor(
        private llamadaService: LlamadaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.llamadaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'llamadaListModification',
                content: 'Deleted an llamada'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-llamada-delete-popup',
    template: ''
})
export class LlamadaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private llamadaPopupService: LlamadaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.llamadaPopupService
                .open(LlamadaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
