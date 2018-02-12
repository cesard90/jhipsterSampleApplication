/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HabitacionDialogComponent } from '../../../../../../main/webapp/app/entities/habitacion/habitacion-dialog.component';
import { HabitacionService } from '../../../../../../main/webapp/app/entities/habitacion/habitacion.service';
import { Habitacion } from '../../../../../../main/webapp/app/entities/habitacion/habitacion.model';
import { HotelService } from '../../../../../../main/webapp/app/entities/hotel';
import { CategoriaService } from '../../../../../../main/webapp/app/entities/categoria';
import { LlamadaService } from '../../../../../../main/webapp/app/entities/llamada';
import { TipoHabitacionService } from '../../../../../../main/webapp/app/entities/tipo-habitacion';
import { ReservaService } from '../../../../../../main/webapp/app/entities/reserva';

describe('Component Tests', () => {

    describe('Habitacion Management Dialog Component', () => {
        let comp: HabitacionDialogComponent;
        let fixture: ComponentFixture<HabitacionDialogComponent>;
        let service: HabitacionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HabitacionDialogComponent],
                providers: [
                    HotelService,
                    CategoriaService,
                    LlamadaService,
                    TipoHabitacionService,
                    ReservaService,
                    HabitacionService
                ]
            })
            .overrideTemplate(HabitacionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HabitacionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HabitacionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Habitacion(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.habitacion = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'habitacionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Habitacion();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.habitacion = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'habitacionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
