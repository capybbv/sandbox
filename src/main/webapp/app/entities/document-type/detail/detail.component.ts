import { Component } from '@angular/core';
import { DocumentComponent } from 'app/entities/document/list/document.component';

@Component({
  selector: 'jhi-detail',
  standalone: true,
  imports: [DocumentComponent],
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.scss',
})
export class DetailComponent {}
