import dayjs from 'dayjs/esm';
import { IJobHistory } from 'app/entities/job-history/job-history.model';
import { IDepartment } from 'app/entities/department/department.model';
import { IJob } from 'app/entities/job/job.model';
import { IDocument } from '../document/document.model';

export interface IEmployee {
  employeeId: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  hireDate?: dayjs.Dayjs | null;
  salary?: number | null;
  commissionPct?: number | null;
  subEmployees?: IEmployee[] | null;
  jobHistories?: IJobHistory[] | null;
  managedDepartments?: IDepartment[] | null;
  job?: IJob | null;
  manager?: IEmployee | null;
  department?: IDepartment | null;
  documents?: IDocument[] | null;
}

export type NewEmployee = Omit<IEmployee, 'employeeId'> & { employeeId: null };
