/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TipoLlamadaDetailComponent } from '../../../../../../main/webapp/app/entities/tipo-llamada/tipo-llamada-detail.component';
import { TipoLlamadaService } from '../../../../../../main/webapp/app/entities/tipo-llamada/tipo-llamada.service';
import { TipoLlamada } from '../../../../../../main/webapp/app/entities/tipo-llamada/tipo-llamada.model';

describe('Component Tests', () => {

    describe('TipoLlamada Management Detail Component', () => {
        let comp: TipoLlamadaDetailComponent;
        let fixture: ComponentFixture<TipoLlamadaDetailComponent>;
        let service: TipoLlamadaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TipoLlamadaDetailComponent],
                providers: [
                    TipoLlamadaService
                ]
            })
            .overrideTemplate(TipoLlamadaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoLlamadaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoLlamadaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TipoLlamada(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tipoLlamada).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
