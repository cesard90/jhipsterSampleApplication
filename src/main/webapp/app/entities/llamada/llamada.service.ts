import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Llamada } from './llamada.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Llamada>;

@Injectable()
export class LlamadaService {

    private resourceUrl =  SERVER_API_URL + 'api/llamadas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/llamadas';

    constructor(private http: HttpClient) { }

    create(llamada: Llamada): Observable<EntityResponseType> {
        const copy = this.convert(llamada);
        return this.http.post<Llamada>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(llamada: Llamada): Observable<EntityResponseType> {
        const copy = this.convert(llamada);
        return this.http.put<Llamada>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Llamada>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Llamada[]>> {
        const options = createRequestOption(req);
        return this.http.get<Llamada[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Llamada[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Llamada[]>> {
        const options = createRequestOption(req);
        return this.http.get<Llamada[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Llamada[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Llamada = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Llamada[]>): HttpResponse<Llamada[]> {
        const jsonResponse: Llamada[] = res.body;
        const body: Llamada[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Llamada.
     */
    private convertItemFromServer(llamada: Llamada): Llamada {
        const copy: Llamada = Object.assign({}, llamada);
        return copy;
    }

    /**
     * Convert a Llamada to a JSON which can be sent to the server.
     */
    private convert(llamada: Llamada): Llamada {
        const copy: Llamada = Object.assign({}, llamada);
        return copy;
    }
}
