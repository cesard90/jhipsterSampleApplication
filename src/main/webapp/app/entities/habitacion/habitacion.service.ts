import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Habitacion } from './habitacion.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Habitacion>;

@Injectable()
export class HabitacionService {

    private resourceUrl =  SERVER_API_URL + 'api/habitacions';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/habitacions';

    constructor(private http: HttpClient) { }

    create(habitacion: Habitacion): Observable<EntityResponseType> {
        const copy = this.convert(habitacion);
        return this.http.post<Habitacion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(habitacion: Habitacion): Observable<EntityResponseType> {
        const copy = this.convert(habitacion);
        return this.http.put<Habitacion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Habitacion>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Habitacion[]>> {
        const options = createRequestOption(req);
        return this.http.get<Habitacion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Habitacion[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Habitacion[]>> {
        const options = createRequestOption(req);
        return this.http.get<Habitacion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Habitacion[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Habitacion = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Habitacion[]>): HttpResponse<Habitacion[]> {
        const jsonResponse: Habitacion[] = res.body;
        const body: Habitacion[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Habitacion.
     */
    private convertItemFromServer(habitacion: Habitacion): Habitacion {
        const copy: Habitacion = Object.assign({}, habitacion);
        return copy;
    }

    /**
     * Convert a Habitacion to a JSON which can be sent to the server.
     */
    private convert(habitacion: Habitacion): Habitacion {
        const copy: Habitacion = Object.assign({}, habitacion);
        return copy;
    }
}
