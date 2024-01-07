import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, TemplateRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgForOf } from '@angular/common';

import { IDocument, IDocumentUpdateReq } from '../document.model';
import SharedModule from '../../../shared/shared.module';
import { AlertService } from '../../../core/util/alert.service';
import { DocumentService } from '../service/document.service';
import { IDocumentType } from '../../document-type/document-type.model';
import { DocumentTypeService } from '../../document-type/service/document-type.service';
import { Observable, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-document-update',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, NgForOf],
  templateUrl: './document-update.component.html',
  styleUrl: './document-update.component.scss',
})
export class DocumentUpdateComponent implements OnInit, OnChanges {
  // @Input() id?: number;
  @Input() employeeId!: number;
  @Input() isShowModal!: boolean;
  @Output() isShowModalChange = new EventEmitter<boolean>();
  @Input() selectedDocumentId!: number | undefined;
  @Output() selectedDocumentIdChange = new EventEmitter<number | undefined>();

  form!: FormGroup;
  title!: string;
  loading = false;
  submitting = false;
  submitted = false;

  document: IDocument | null = null;

  documentTypesSharedCollection: IDocumentType[] = [];

  @ViewChild('content') myModalContent!: TemplateRef<any>;

  constructor(
    private modalService: NgbModal,
    private formBuilder: FormBuilder,
    // private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService,
    private documentService: DocumentService,
    private documentTypeService: DocumentTypeService,
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      documentName: ['jj', Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      documentType: [this.documentTypesSharedCollection[0], Validators.required],
    });

    this.title = 'Create a new Document';
    if (this.selectedDocumentId) {
      // Edit Mode
      this.title = 'Edit Document';
      this.loading = true;
      this.documentService.getDocument(this.selectedDocumentId).subscribe({
        next: (document: IDocument) => {
          this.form.patchValue(document);
          this.loading = false;
        },
        error: () =>
          this.alertService.addAlert({
            type: 'danger',
            message: 'Error retrieving data!',
          }),
      });
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
    if (changes['isShowModal'] && this.isShowModal) {
      this.open(this.myModalContent); // Replace `myModalContent` with your modal template reference
    }
  }

  onReset(): void {
    this.submitted = false;
    // this.form.reset();
  }

  get f(): any {
    return this.form.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    this.alertService.clear();

    this.document = this.form.getRawValue() as IDocument;
    console.warn('[*]: document in form', this.document);
    const documentUpdateReq: IDocumentUpdateReq = {
      documentName: this.document.documentName ?? 'k',
      documentTypeId: this.document.documentType?.documentTypeId ?? 0,
      employeeId: this.employeeId,
    };

    if (this.selectedDocumentId !== undefined) {
      this.documentService.update(this.selectedDocumentId, documentUpdateReq).subscribe({
        next: () => {
          this.dismissModal('Update successfully!');
        },
        error: (error: any) => {
          console.warn('[*] Update document error: ', error);
          this.submitting = false;
        },
      });
    } else {
      this.documentService.create(documentUpdateReq).subscribe({
        next: () => {
          this.dismissModal('Create successfully!');
        },
        error: (error: any) => {
          console.warn('[*] Create document error: ', error);
          this.submitting = false;
        },
      });
    }
  }

  open(content: TemplateRef<any>): void {
    this.onReset();
    this.loadDocumentTypes();

    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' });
  }

  dismissModal(reason?: string): void {
    this.submitting = false;
    this.modalService.dismissAll(reason);
    this.handleModalClose();
    window.location.reload();
  }

  previousState(): void {
    window.history.back();
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.submitting = false;
  }

  private loadDocumentTypes(): void {
    this.documentTypeService.findAll().subscribe({
      next: (documentTypes: IDocumentType[]) => {
        this.documentTypesSharedCollection = documentTypes;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  private handleModalClose(): void {
    // Emit an event or call a method to notify the parent component
    this.isShowModalChange.emit(false);
  }
}
