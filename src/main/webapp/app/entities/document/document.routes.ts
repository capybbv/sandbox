import { Routes } from '@angular/router';
import { DocumentComponent } from './list/document.component';
import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const documentRoutes: Routes = [
  {
    path: '',
    component: DocumentComponent,
    data: {
      defaultSort: 'documentId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default documentRoutes;
