import { IDocumentType } from '../document-type/document-type.model';
import { IEmployee } from '../employee/employee.model';

export interface IDocument {
  documentId: number;
  documentName?: string;
  documentType?: IDocumentType | null;
  employee?: IEmployee | null;
}

export interface IDocumentUpdateReq {
  documentName: string;
  documentTypeId: number;
  employeeId: number;
}

export interface CreateDocument {
  documentName: string;
  documentTypeId: number;
  employeeId: number;
}
export type NewDocument = Omit<IDocument, 'documentId'> & { documentId: null };
