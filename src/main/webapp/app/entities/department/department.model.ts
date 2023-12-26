import { IEmployee } from 'app/entities/employee/employee.model';
import { IJobHistory } from 'app/entities/job-history/job-history.model';
import { ILocation } from 'app/entities/location/location.model';

export interface IDepartment {
  departmentId: number;
  departmentName?: string | null;
  employees?: IEmployee[] | null;
  jobHistories?: IJobHistory[] | null;
  manager?: IEmployee | null;
  location?: ILocation | null;
}

export type NewDepartment = Omit<IDepartment, 'departmentId'> & { departmentId: null };
