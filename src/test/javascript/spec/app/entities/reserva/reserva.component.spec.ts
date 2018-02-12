/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ReservaComponent } from '../../../../../../main/webapp/app/entities/reserva/reserva.component';
import { ReservaService } from '../../../../../../main/webapp/app/entities/reserva/reserva.service';
import { Reserva } from '../../../../../../main/webapp/app/entities/reserva/reserva.model';

describe('Component Tests', () => {

    describe('Reserva Management Component', () => {
        let comp: ReservaComponent;
        let fixture: ComponentFixture<ReservaComponent>;
        let service: ReservaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [ReservaComponent],
                providers: [
                    ReservaService
                ]
            })
            .overrideTemplate(ReservaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReservaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReservaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Reserva(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.reservas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
