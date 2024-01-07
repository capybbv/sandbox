import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IDocument, NewDocument } from '../document.model';

type PartialWithRequiredKeyOf<T extends { documentId: unknown }> = Partial<Omit<T, 'documentId'>> & { documentId: T['documentId'] };

type DocumentFormGroupInput = IDocument | PartialWithRequiredKeyOf<NewDocument>;

type DocumentFormDefaults = Pick<NewDocument, 'documentId'>;

type DocumentFormGroupContent = {
  documentId: FormControl<IDocument['documentId'] | NewDocument['documentId']>;
  documentName: FormControl<IDocument['documentName']>;
  documentType: FormControl<IDocument['documentType']>;
};

export type DocumentFormGroup = FormGroup<DocumentFormGroupContent>;

@Injectable({
  providedIn: 'root',
})
export class DocumentFormService {
  createDocumentFormGroup(document: DocumentFormGroupInput = { documentId: null }): DocumentFormGroup {
    const documentRawValue = {
      ...this.getFormDefaults(),
      ...document,
    };
    return new FormGroup<DocumentFormGroupContent>(<DocumentFormGroupContent>{
      documentId: new FormControl(
        { value: documentRawValue.documentId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      documentName: new FormControl(documentRawValue.documentName),
      documentType: new FormControl(documentRawValue.documentType),
    });
  }

  getDocument(form: DocumentFormGroup): IDocument | NewDocument {
    return form.getRawValue() as IDocument | NewDocument;
  }

  resetForm(form: DocumentFormGroup, document: DocumentFormGroupInput): void {
    const documentRawValue = { ...this.getFormDefaults(), ...document };
    form.reset({
      ...documentRawValue,
      documentId: { value: documentRawValue.documentId, disabled: true },
    } as any);
  }

  private getFormDefaults(): DocumentFormDefaults {
    return {
      documentId: null,
    };
  }
}
