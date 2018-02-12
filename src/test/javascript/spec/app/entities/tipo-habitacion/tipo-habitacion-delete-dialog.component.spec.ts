/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TipoHabitacionDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/tipo-habitacion/tipo-habitacion-delete-dialog.component';
import { TipoHabitacionService } from '../../../../../../main/webapp/app/entities/tipo-habitacion/tipo-habitacion.service';

describe('Component Tests', () => {

    describe('TipoHabitacion Management Delete Component', () => {
        let comp: TipoHabitacionDeleteDialogComponent;
        let fixture: ComponentFixture<TipoHabitacionDeleteDialogComponent>;
        let service: TipoHabitacionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TipoHabitacionDeleteDialogComponent],
                providers: [
                    TipoHabitacionService
                ]
            })
            .overrideTemplate(TipoHabitacionDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoHabitacionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoHabitacionService);
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
