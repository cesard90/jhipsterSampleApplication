/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ReservaDetailComponent } from '../../../../../../main/webapp/app/entities/reserva/reserva-detail.component';
import { ReservaService } from '../../../../../../main/webapp/app/entities/reserva/reserva.service';
import { Reserva } from '../../../../../../main/webapp/app/entities/reserva/reserva.model';

describe('Component Tests', () => {

    describe('Reserva Management Detail Component', () => {
        let comp: ReservaDetailComponent;
        let fixture: ComponentFixture<ReservaDetailComponent>;
        let service: ReservaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [ReservaDetailComponent],
                providers: [
                    ReservaService
                ]
            })
            .overrideTemplate(ReservaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReservaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReservaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Reserva(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.reserva).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
