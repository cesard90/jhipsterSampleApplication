import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TipoHabitacion } from './tipo-habitacion.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TipoHabitacion>;

@Injectable()
export class TipoHabitacionService {

    private resourceUrl =  SERVER_API_URL + 'api/tipo-habitacions';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-habitacions';

    constructor(private http: HttpClient) { }

    create(tipoHabitacion: TipoHabitacion): Observable<EntityResponseType> {
        const copy = this.convert(tipoHabitacion);
        return this.http.post<TipoHabitacion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tipoHabitacion: TipoHabitacion): Observable<EntityResponseType> {
        const copy = this.convert(tipoHabitacion);
        return this.http.put<TipoHabitacion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TipoHabitacion>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TipoHabitacion[]>> {
        const options = createRequestOption(req);
        return this.http.get<TipoHabitacion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TipoHabitacion[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<TipoHabitacion[]>> {
        const options = createRequestOption(req);
        return this.http.get<TipoHabitacion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TipoHabitacion[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TipoHabitacion = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TipoHabitacion[]>): HttpResponse<TipoHabitacion[]> {
        const jsonResponse: TipoHabitacion[] = res.body;
        const body: TipoHabitacion[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TipoHabitacion.
     */
    private convertItemFromServer(tipoHabitacion: TipoHabitacion): TipoHabitacion {
        const copy: TipoHabitacion = Object.assign({}, tipoHabitacion);
        return copy;
    }

    /**
     * Convert a TipoHabitacion to a JSON which can be sent to the server.
     */
    private convert(tipoHabitacion: TipoHabitacion): TipoHabitacion {
        const copy: TipoHabitacion = Object.assign({}, tipoHabitacion);
        return copy;
    }
}
