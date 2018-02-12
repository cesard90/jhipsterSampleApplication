/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TipoLlamadaComponent } from '../../../../../../main/webapp/app/entities/tipo-llamada/tipo-llamada.component';
import { TipoLlamadaService } from '../../../../../../main/webapp/app/entities/tipo-llamada/tipo-llamada.service';
import { TipoLlamada } from '../../../../../../main/webapp/app/entities/tipo-llamada/tipo-llamada.model';

describe('Component Tests', () => {

    describe('TipoLlamada Management Component', () => {
        let comp: TipoLlamadaComponent;
        let fixture: ComponentFixture<TipoLlamadaComponent>;
        let service: TipoLlamadaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TipoLlamadaComponent],
                providers: [
                    TipoLlamadaService
                ]
            })
            .overrideTemplate(TipoLlamadaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoLlamadaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoLlamadaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TipoLlamada(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tipoLlamadas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
