/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LlamadaComponent } from '../../../../../../main/webapp/app/entities/llamada/llamada.component';
import { LlamadaService } from '../../../../../../main/webapp/app/entities/llamada/llamada.service';
import { Llamada } from '../../../../../../main/webapp/app/entities/llamada/llamada.model';

describe('Component Tests', () => {

    describe('Llamada Management Component', () => {
        let comp: LlamadaComponent;
        let fixture: ComponentFixture<LlamadaComponent>;
        let service: LlamadaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [LlamadaComponent],
                providers: [
                    LlamadaService
                ]
            })
            .overrideTemplate(LlamadaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LlamadaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LlamadaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Llamada(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.llamadas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
