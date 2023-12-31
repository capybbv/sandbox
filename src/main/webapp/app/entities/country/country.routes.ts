import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CountryComponent } from './list/country.component';
import { CountryDetailComponent } from './detail/country-detail.component';
import { CountryUpdateComponent } from './update/country-update.component';
import CountryResolve from './route/country-routing-resolve.service';

const countryRoute: Routes = [
  {
    path: '',
    component: CountryComponent,
    data: {
      defaultSort: 'countryId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':countryId/view',
    component: CountryDetailComponent,
    resolve: {
      country: CountryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CountryUpdateComponent,
    resolve: {
      country: CountryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':countryId/edit',
    component: CountryUpdateComponent,
    resolve: {
      country: CountryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default countryRoute;
