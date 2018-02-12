/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HabitacionDetailComponent } from '../../../../../../main/webapp/app/entities/habitacion/habitacion-detail.component';
import { HabitacionService } from '../../../../../../main/webapp/app/entities/habitacion/habitacion.service';
import { Habitacion } from '../../../../../../main/webapp/app/entities/habitacion/habitacion.model';

describe('Component Tests', () => {

    describe('Habitacion Management Detail Component', () => {
        let comp: HabitacionDetailComponent;
        let fixture: ComponentFixture<HabitacionDetailComponent>;
        let service: HabitacionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HabitacionDetailComponent],
                providers: [
                    HabitacionService
                ]
            })
            .overrideTemplate(HabitacionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HabitacionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HabitacionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Habitacion(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.habitacion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
