import { IJob, NewJob } from './job.model';

export const sampleWithRequiredData: IJob = {
  jobId: 'd5607fc3-986a-4d92-b460-d88937e67402',
};

export const sampleWithPartialData: IJob = {
  jobId: '39566f66-17ac-4691-938a-fae83ac83dc3',
  minSalary: 5906,
};

export const sampleWithFullData: IJob = {
  jobId: 'a51efeca-b950-4b84-9ab6-062164381057',
  jobTitle: 'National Web Facilitator',
  minSalary: 2235,
  maxSalary: 4367,
};

export const sampleWithNewData: NewJob = {
  jobId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
