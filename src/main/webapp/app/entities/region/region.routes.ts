import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RegionComponent } from './list/region.component';
import { RegionDetailComponent } from './detail/region-detail.component';
import { RegionUpdateComponent } from './update/region-update.component';
import RegionResolve from './route/region-routing-resolve.service';

const regionRoute: Routes = [
  {
    path: '',
    component: RegionComponent,
    data: {
      defaultSort: 'regionId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':regionId/view',
    component: RegionDetailComponent,
    resolve: {
      region: RegionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RegionUpdateComponent,
    resolve: {
      region: RegionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':regionId/edit',
    component: RegionUpdateComponent,
    resolve: {
      region: RegionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default regionRoute;
