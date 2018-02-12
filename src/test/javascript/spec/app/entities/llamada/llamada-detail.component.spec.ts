/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LlamadaDetailComponent } from '../../../../../../main/webapp/app/entities/llamada/llamada-detail.component';
import { LlamadaService } from '../../../../../../main/webapp/app/entities/llamada/llamada.service';
import { Llamada } from '../../../../../../main/webapp/app/entities/llamada/llamada.model';

describe('Component Tests', () => {

    describe('Llamada Management Detail Component', () => {
        let comp: LlamadaDetailComponent;
        let fixture: ComponentFixture<LlamadaDetailComponent>;
        let service: LlamadaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [LlamadaDetailComponent],
                providers: [
                    LlamadaService
                ]
            })
            .overrideTemplate(LlamadaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LlamadaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LlamadaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Llamada(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.llamada).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
