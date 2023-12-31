import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { LocationComponent } from './list/location.component';
import { LocationDetailComponent } from './detail/location-detail.component';
import { LocationUpdateComponent } from './update/location-update.component';
import LocationResolve from './route/location-routing-resolve.service';

const locationRoute: Routes = [
  {
    path: '',
    component: LocationComponent,
    data: {
      defaultSort: 'locationId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':locationId/view',
    component: LocationDetailComponent,
    resolve: {
      location: LocationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LocationUpdateComponent,
    resolve: {
      location: LocationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':locationId/edit',
    component: LocationUpdateComponent,
    resolve: {
      location: LocationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default locationRoute;
