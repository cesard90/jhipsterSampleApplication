/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TipoHabitacionComponent } from '../../../../../../main/webapp/app/entities/tipo-habitacion/tipo-habitacion.component';
import { TipoHabitacionService } from '../../../../../../main/webapp/app/entities/tipo-habitacion/tipo-habitacion.service';
import { TipoHabitacion } from '../../../../../../main/webapp/app/entities/tipo-habitacion/tipo-habitacion.model';

describe('Component Tests', () => {

    describe('TipoHabitacion Management Component', () => {
        let comp: TipoHabitacionComponent;
        let fixture: ComponentFixture<TipoHabitacionComponent>;
        let service: TipoHabitacionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TipoHabitacionComponent],
                providers: [
                    TipoHabitacionService
                ]
            })
            .overrideTemplate(TipoHabitacionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoHabitacionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoHabitacionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TipoHabitacion(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tipoHabitacions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
