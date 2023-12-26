import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DepartmentService } from '../service/department.service';

import { DepartmentComponent } from './department.component';
import SpyInstance = jest.SpyInstance;

describe('Department Management Component', () => {
  let comp: DepartmentComponent;
  let fixture: ComponentFixture<DepartmentComponent>;
  let service: DepartmentService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'department', component: DepartmentComponent }]),
        HttpClientTestingModule,
        DepartmentComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'departmentId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'departmentId,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(DepartmentComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DepartmentComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DepartmentService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ departmentId: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.departments?.[0]).toEqual(expect.objectContaining({ departmentId: 123 }));
  });

  describe('trackDepartmentId', () => {
    it('Should forward to departmentService', () => {
      const entity = { departmentId: 123 };
      jest.spyOn(service, 'getDepartmentIdentifier');
      const departmentId = comp.trackDepartmentId(0, entity);
      expect(service.getDepartmentIdentifier).toHaveBeenCalledWith(entity);
      expect(departmentId).toBe(entity.departmentId);
    });
  });

  it('should load a page', () => {
    // WHEN
    comp.navigateToPage(1);

    // THEN
    expect(routerNavigateSpy).toHaveBeenCalled();
  });

  it('should calculate the sort attribute for an id', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['departmentId,desc'] }));
  });

  it('should calculate the sort attribute for a non-id attribute', () => {
    // GIVEN
    comp.predicate = 'name';

    // WHEN
    comp.navigateToWithComponentValues();

    // THEN
    expect(routerNavigateSpy).toHaveBeenLastCalledWith(
      expect.anything(),
      expect.objectContaining({
        queryParams: expect.objectContaining({
          sort: ['name,asc'],
        }),
      }),
    );
  });
});
