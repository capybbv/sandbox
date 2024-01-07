import { MetricsEndpointsRequestsComponent } from './../../../admin/metrics/blocks/metrics-endpoints-requests/metrics-endpoints-requests.component';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEmployee } from '../employee.model';
import { DocumentComponent } from 'app/entities/document/list/document.component';
import { DocumentUpdateComponent } from '../../document/update/document-update.component';
import { NgIf } from '@angular/common';

@Component({
  standalone: true,
  selector: 'jhi-employee-detail',
  templateUrl: './employee-detail.component.html',
  imports: [
    SharedModule,
    RouterModule,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    DocumentComponent,
    DocumentUpdateComponent,
    NgIf,
  ],
})
export class EmployeeDetailComponent implements OnInit {
  @Input() employee: IEmployee | null = null;
  isShowCreateDocumentModal = false;

  constructor(protected activatedRoute: ActivatedRoute) {
    // console.warn('EmployeeDetailComponent constructor', this.employee);
  }

  ngOnInit(): void {
    // console.warn('EmployeeDetailComponent ngOnInit', this.employee);
  }

  previousState(): void {
    window.history.back();
  }
}
