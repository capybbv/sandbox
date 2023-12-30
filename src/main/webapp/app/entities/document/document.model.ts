import { IDocumentType } from '../document-type/document-type.model';
import { IEmployee } from '../employee/employee.model';

export interface IDocument {
  documentId?: number;
  documentName?: string;
  documentType?: IDocumentType | null;
  employee?: IEmployee | null;
}

export type NewDocument = Omit<IDocument, 'id'> & { id: null };
