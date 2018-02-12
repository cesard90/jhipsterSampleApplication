/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TipoHabitacionDetailComponent } from '../../../../../../main/webapp/app/entities/tipo-habitacion/tipo-habitacion-detail.component';
import { TipoHabitacionService } from '../../../../../../main/webapp/app/entities/tipo-habitacion/tipo-habitacion.service';
import { TipoHabitacion } from '../../../../../../main/webapp/app/entities/tipo-habitacion/tipo-habitacion.model';

describe('Component Tests', () => {

    describe('TipoHabitacion Management Detail Component', () => {
        let comp: TipoHabitacionDetailComponent;
        let fixture: ComponentFixture<TipoHabitacionDetailComponent>;
        let service: TipoHabitacionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TipoHabitacionDetailComponent],
                providers: [
                    TipoHabitacionService
                ]
            })
            .overrideTemplate(TipoHabitacionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoHabitacionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoHabitacionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TipoHabitacion(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tipoHabitacion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
