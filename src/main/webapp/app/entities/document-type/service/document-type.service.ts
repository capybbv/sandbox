import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { IDocumentType } from '../document-type.model';
import { Observable, Observer } from 'rxjs';

class AoiConfigService {}

@Injectable({
  providedIn: 'root',
})
export class DocumentTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/document-types');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  findAll(): Observable<IDocumentType[]> {
    return this.http.get<IDocumentType[]>(`${this.resourceUrl}/all`);
  }
}
