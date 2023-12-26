import { IEmployee } from 'app/entities/employee/employee.model';
import { IJobHistory } from 'app/entities/job-history/job-history.model';

export interface IJob {
  jobId: string;
  jobTitle?: string | null;
  minSalary?: number | null;
  maxSalary?: number | null;
  employees?: IEmployee[] | null;
  jobHistories?: IJobHistory[] | null;
}

export type NewJob = Omit<IJob, 'jobId'> & { jobId: null };
