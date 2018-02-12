import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Categoria } from './categoria.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Categoria>;

@Injectable()
export class CategoriaService {

    private resourceUrl =  SERVER_API_URL + 'api/categorias';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/categorias';

    constructor(private http: HttpClient) { }

    create(categoria: Categoria): Observable<EntityResponseType> {
        const copy = this.convert(categoria);
        return this.http.post<Categoria>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(categoria: Categoria): Observable<EntityResponseType> {
        const copy = this.convert(categoria);
        return this.http.put<Categoria>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Categoria>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Categoria[]>> {
        const options = createRequestOption(req);
        return this.http.get<Categoria[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Categoria[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Categoria[]>> {
        const options = createRequestOption(req);
        return this.http.get<Categoria[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Categoria[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Categoria = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Categoria[]>): HttpResponse<Categoria[]> {
        const jsonResponse: Categoria[] = res.body;
        const body: Categoria[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Categoria.
     */
    private convertItemFromServer(categoria: Categoria): Categoria {
        const copy: Categoria = Object.assign({}, categoria);
        return copy;
    }

    /**
     * Convert a Categoria to a JSON which can be sent to the server.
     */
    private convert(categoria: Categoria): Categoria {
        const copy: Categoria = Object.assign({}, categoria);
        return copy;
    }
}
