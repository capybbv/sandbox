import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CountryService } from '../service/country.service';

import { CountryComponent } from './country.component';
import SpyInstance = jest.SpyInstance;

describe('Country Management Component', () => {
  let comp: CountryComponent;
  let fixture: ComponentFixture<CountryComponent>;
  let service: CountryService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'country', component: CountryComponent }]),
        HttpClientTestingModule,
        CountryComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'countryId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'countryId,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(CountryComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CountryComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CountryService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ countryId: 'ABC' }],
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
    expect(comp.countries?.[0]).toEqual(expect.objectContaining({ countryId: 'ABC' }));
  });

  describe('trackCountryId', () => {
    it('Should forward to countryService', () => {
      const entity = { countryId: 'ABC' };
      jest.spyOn(service, 'getCountryIdentifier');
      const countryId = comp.trackCountryId(0, entity);
      expect(service.getCountryIdentifier).toHaveBeenCalledWith(entity);
      expect(countryId).toBe(entity.countryId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['countryId,desc'] }));
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
