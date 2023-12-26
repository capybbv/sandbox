import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'employee',
    data: { pageTitle: 'hrApp.employee.home.title' },
    loadChildren: () => import('./employee/employee.routes'),
  },
  {
    path: 'job',
    data: { pageTitle: 'hrApp.job.home.title' },
    loadChildren: () => import('./job/job.routes'),
  },
  {
    path: 'job-history',
    data: { pageTitle: 'hrApp.jobHistory.home.title' },
    loadChildren: () => import('./job-history/job-history.routes'),
  },
  {
    path: 'department',
    data: { pageTitle: 'hrApp.department.home.title' },
    loadChildren: () => import('./department/department.routes'),
  },
  {
    path: 'location',
    data: { pageTitle: 'hrApp.location.home.title' },
    loadChildren: () => import('./location/location.routes'),
  },
  {
    path: 'country',
    data: { pageTitle: 'hrApp.country.home.title' },
    loadChildren: () => import('./country/country.routes'),
  },
  {
    path: 'region',
    data: { pageTitle: 'hrApp.region.home.title' },
    loadChildren: () => import('./region/region.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
