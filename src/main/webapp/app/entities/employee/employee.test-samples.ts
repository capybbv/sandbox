import dayjs from 'dayjs/esm';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  employeeId: 19077,
};

export const sampleWithPartialData: IEmployee = {
  employeeId: 23440,
  lastName: 'Corwin-Luettgen',
  phoneNumber: 'authentic',
  salary: 3032,
};

export const sampleWithFullData: IEmployee = {
  employeeId: 11246,
  firstName: 'Janessa',
  lastName: 'Leuschke',
  email: 'Mayra_Hartmann@hotmail.com',
  phoneNumber: 'failing fond',
  hireDate: dayjs('2023-12-26T07:56'),
  salary: 3769,
  commissionPct: 1795,
};

export const sampleWithNewData: NewEmployee = {
  employeeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
