import dayjs from 'dayjs/esm';

import { IJobHistory, NewJobHistory } from './job-history.model';

export const sampleWithRequiredData: IJobHistory = {
  id: 1127,
};

export const sampleWithPartialData: IJobHistory = {
  id: 16792,
  employeeId: 7985,
  salary: 3380,
};

export const sampleWithFullData: IJobHistory = {
  id: 12486,
  employeeId: 11760,
  startDate: dayjs('2023-12-25T12:25'),
  endDate: dayjs('2023-12-25T18:41'),
  salary: 23606,
};

export const sampleWithNewData: NewJobHistory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
