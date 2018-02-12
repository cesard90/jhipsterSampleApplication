import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Reserva } from './reserva.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Reserva>;

@Injectable()
export class ReservaService {

    private resourceUrl =  SERVER_API_URL + 'api/reservas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/reservas';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(reserva: Reserva): Observable<EntityResponseType> {
        const copy = this.convert(reserva);
        return this.http.post<Reserva>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(reserva: Reserva): Observable<EntityResponseType> {
        const copy = this.convert(reserva);
        return this.http.put<Reserva>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Reserva>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Reserva[]>> {
        const options = createRequestOption(req);
        return this.http.get<Reserva[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Reserva[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Reserva[]>> {
        const options = createRequestOption(req);
        return this.http.get<Reserva[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Reserva[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Reserva = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Reserva[]>): HttpResponse<Reserva[]> {
        const jsonResponse: Reserva[] = res.body;
        const body: Reserva[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Reserva.
     */
    private convertItemFromServer(reserva: Reserva): Reserva {
        const copy: Reserva = Object.assign({}, reserva);
        copy.fechaInicio = this.dateUtils
            .convertDateTimeFromServer(reserva.fechaInicio);
        copy.fechaFin = this.dateUtils
            .convertDateTimeFromServer(reserva.fechaFin);
        copy.fechaReserva = this.dateUtils
            .convertDateTimeFromServer(reserva.fechaReserva);
        return copy;
    }

    /**
     * Convert a Reserva to a JSON which can be sent to the server.
     */
    private convert(reserva: Reserva): Reserva {
        const copy: Reserva = Object.assign({}, reserva);

        copy.fechaInicio = this.dateUtils.toDate(reserva.fechaInicio);

        copy.fechaFin = this.dateUtils.toDate(reserva.fechaFin);

        copy.fechaReserva = this.dateUtils.toDate(reserva.fechaReserva);
        return copy;
    }
}
