import { Component, Input, OnInit } from '@angular/core';
import { IDocument } from '../document.model';
import { DocumentService } from '../service/document.service';
import { ActivatedRoute } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { DocumentUpdateComponent } from '../update/document-update.component';

@Component({
  selector: 'jhi-document-list',
  standalone: true,
  imports: [CommonModule, DocumentUpdateComponent],
  templateUrl: './document.component.html',
  styleUrl: './document.component.scss',
})
export class DocumentComponent implements OnInit {
  @Input() employeeId!: number;
  isShowModal: boolean = false;

  documents?: IDocument[];
  isLoading = false;
  selectedDocumentId?: number = undefined;

  constructor(
    protected documentService: DocumentService,
    protected activatedRoute: ActivatedRoute,
    modalService: NgbModal,
  ) {}

  ngOnInit(): void {
    this.documents = new Array<IDocument>();
    console.log('Employee ID: ' + this.employeeId);

    this.documentService.findByEmployeeId(this.employeeId).subscribe((r: HttpResponse<IDocument[]>) => {
      this.documents = r.body ?? [];
    });
  }

  create = (): void => {
    this.isShowModal = true;
  };

  delete(documentId: number): void {
    this.selectedDocumentId = documentId; // Store the selected document ID
  }

  // In DocumentComponent class
  update(documentId: number): void {
    this.selectedDocumentId = documentId; // Store the selected document ID
    this.isShowModal = true; // Set isShowModal to true to open the modal
  }
}
