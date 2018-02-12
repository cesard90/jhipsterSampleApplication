/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TipoLlamadaDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/tipo-llamada/tipo-llamada-delete-dialog.component';
import { TipoLlamadaService } from '../../../../../../main/webapp/app/entities/tipo-llamada/tipo-llamada.service';

describe('Component Tests', () => {

    describe('TipoLlamada Management Delete Component', () => {
        let comp: TipoLlamadaDeleteDialogComponent;
        let fixture: ComponentFixture<TipoLlamadaDeleteDialogComponent>;
        let service: TipoLlamadaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TipoLlamadaDeleteDialogComponent],
                providers: [
                    TipoLlamadaService
                ]
            })
            .overrideTemplate(TipoLlamadaDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoLlamadaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoLlamadaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
