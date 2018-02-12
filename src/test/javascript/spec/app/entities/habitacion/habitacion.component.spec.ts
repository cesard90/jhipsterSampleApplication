/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HabitacionComponent } from '../../../../../../main/webapp/app/entities/habitacion/habitacion.component';
import { HabitacionService } from '../../../../../../main/webapp/app/entities/habitacion/habitacion.service';
import { Habitacion } from '../../../../../../main/webapp/app/entities/habitacion/habitacion.model';

describe('Component Tests', () => {

    describe('Habitacion Management Component', () => {
        let comp: HabitacionComponent;
        let fixture: ComponentFixture<HabitacionComponent>;
        let service: HabitacionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HabitacionComponent],
                providers: [
                    HabitacionService
                ]
            })
            .overrideTemplate(HabitacionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HabitacionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HabitacionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Habitacion(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.habitacions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
