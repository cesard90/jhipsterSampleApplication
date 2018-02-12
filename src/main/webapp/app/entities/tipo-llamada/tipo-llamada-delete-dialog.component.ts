import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoLlamada } from './tipo-llamada.model';
import { TipoLlamadaPopupService } from './tipo-llamada-popup.service';
import { TipoLlamadaService } from './tipo-llamada.service';

@Component({
    selector: 'jhi-tipo-llamada-delete-dialog',
    templateUrl: './tipo-llamada-delete-dialog.component.html'
})
export class TipoLlamadaDeleteDialogComponent {

    tipoLlamada: TipoLlamada;

    constructor(
        private tipoLlamadaService: TipoLlamadaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoLlamadaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipoLlamadaListModification',
                content: 'Deleted an tipoLlamada'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-llamada-delete-popup',
    template: ''
})
export class TipoLlamadaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoLlamadaPopupService: TipoLlamadaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tipoLlamadaPopupService
                .open(TipoLlamadaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
