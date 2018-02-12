import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TipoLlamada } from './tipo-llamada.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TipoLlamada>;

@Injectable()
export class TipoLlamadaService {

    private resourceUrl =  SERVER_API_URL + 'api/tipo-llamadas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-llamadas';

    constructor(private http: HttpClient) { }

    create(tipoLlamada: TipoLlamada): Observable<EntityResponseType> {
        const copy = this.convert(tipoLlamada);
        return this.http.post<TipoLlamada>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tipoLlamada: TipoLlamada): Observable<EntityResponseType> {
        const copy = this.convert(tipoLlamada);
        return this.http.put<TipoLlamada>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TipoLlamada>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TipoLlamada[]>> {
        const options = createRequestOption(req);
        return this.http.get<TipoLlamada[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TipoLlamada[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<TipoLlamada[]>> {
        const options = createRequestOption(req);
        return this.http.get<TipoLlamada[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TipoLlamada[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TipoLlamada = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TipoLlamada[]>): HttpResponse<TipoLlamada[]> {
        const jsonResponse: TipoLlamada[] = res.body;
        const body: TipoLlamada[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TipoLlamada.
     */
    private convertItemFromServer(tipoLlamada: TipoLlamada): TipoLlamada {
        const copy: TipoLlamada = Object.assign({}, tipoLlamada);
        return copy;
    }

    /**
     * Convert a TipoLlamada to a JSON which can be sent to the server.
     */
    private convert(tipoLlamada: TipoLlamada): TipoLlamada {
        const copy: TipoLlamada = Object.assign({}, tipoLlamada);
        return copy;
    }
}
