import { IDocument } from '../document/document.model';

export interface IDocumentType {
  documentTypeId?: number;
  documentTypeName?: string;
  documents?: IDocument[] | null;
}

export type NewDocumentType = Omit<IDocumentType, 'id'> & { id: null };
