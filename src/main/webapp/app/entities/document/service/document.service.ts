import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { CreateDocumentRequest } from './documentDto';
import { Observable, map } from 'rxjs';
import { IEmployee } from 'app/entities/employee/employee.model';
import { IDocument } from '../document.model';

export type EntityResponseType = Observable<IDocument>;
export type RestDocument = RestOf<IDocument>;

@Injectable({
  providedIn: 'root',
})
export class DocumentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/documents');
  // protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/documents/_search');
  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(document: CreateDocumentRequest): Observable<EntityResponseType> {
    // const copy = this.convertDateFromClient(document);
    return this.http
      .post<RestDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }
}
