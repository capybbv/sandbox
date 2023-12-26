import { IDepartment, NewDepartment } from './department.model';

export const sampleWithRequiredData: IDepartment = {
  departmentId: 26459,
};

export const sampleWithPartialData: IDepartment = {
  departmentId: 20051,
};

export const sampleWithFullData: IDepartment = {
  departmentId: 27781,
  departmentName: 'sans if',
};

export const sampleWithNewData: NewDepartment = {
  departmentId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
