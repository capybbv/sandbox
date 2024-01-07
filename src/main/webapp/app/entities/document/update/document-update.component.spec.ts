import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentUpdateComponent } from './document-update.component';

describe('UpdateComponent', () => {
  let component: DocumentUpdateComponent;
  let fixture: ComponentFixture<DocumentUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocumentUpdateComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DocumentUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});