import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { CreateDocumentRequest } from './documentDto';
import { Observable } from 'rxjs';
import { IDocument, IDocumentUpdateReq } from '../document.model';

@Injectable({
  providedIn: 'root',
})
export class DocumentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/documents');
  protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/documents/_search');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(document: CreateDocumentRequest): Observable<HttpResponse<IDocument>> {
    return this.http.post<IDocument>(this.resourceUrl, document, { observe: 'response' });
  }

  findByEmployeeId(employeeId: number): Observable<HttpResponse<IDocument[]>> {
    return this.http.get<IDocument[]>(`${this.resourceUrl}/employee/${employeeId}`, { observe: 'response' });
  }

  getDocument(documentId: number): Observable<IDocument> {
    return this.http.get<IDocument>(`${this.resourceUrl}/${documentId}`);
  }

  update(documentId: number, document: IDocumentUpdateReq): Observable<HttpResponse<IDocument>> {
    return this.http.patch<IDocument>(`${this.resourceUrl}/${documentId}`, document, { observe: 'response' });
  }
}
