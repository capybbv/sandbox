import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmployee, NewEmployee } from '../employee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { employeeId: unknown }> = Partial<Omit<T, 'employeeId'>> & { employeeId: T['employeeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployee for edit and NewEmployeeFormGroupInput for create.
 */
type EmployeeFormGroupInput = IEmployee | PartialWithRequiredKeyOf<NewEmployee>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmployee | NewEmployee> = Omit<T, 'hireDate'> & {
  hireDate?: string | null;
};

type EmployeeFormRawValue = FormValueOf<IEmployee>;

type NewEmployeeFormRawValue = FormValueOf<NewEmployee>;

type EmployeeFormDefaults = Pick<NewEmployee, 'employeeId' | 'hireDate'>;

type EmployeeFormGroupContent = {
  employeeId: FormControl<EmployeeFormRawValue['employeeId'] | NewEmployee['employeeId']>;
  firstName: FormControl<EmployeeFormRawValue['firstName']>;
  lastName: FormControl<EmployeeFormRawValue['lastName']>;
  email: FormControl<EmployeeFormRawValue['email']>;
  phoneNumber: FormControl<EmployeeFormRawValue['phoneNumber']>;
  hireDate: FormControl<EmployeeFormRawValue['hireDate']>;
  salary: FormControl<EmployeeFormRawValue['salary']>;
  commissionPct: FormControl<EmployeeFormRawValue['commissionPct']>;
  job: FormControl<EmployeeFormRawValue['job']>;
  manager: FormControl<EmployeeFormRawValue['manager']>;
  department: FormControl<EmployeeFormRawValue['department']>;
};

export type EmployeeFormGroup = FormGroup<EmployeeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeFormService {
  createEmployeeFormGroup(employee: EmployeeFormGroupInput = { employeeId: null }): EmployeeFormGroup {
    const employeeRawValue = this.convertEmployeeToEmployeeRawValue({
      ...this.getFormDefaults(),
      ...employee,
    });
    return new FormGroup<EmployeeFormGroupContent>({
      employeeId: new FormControl(
        { value: employeeRawValue.employeeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      firstName: new FormControl(employeeRawValue.firstName),
      lastName: new FormControl(employeeRawValue.lastName),
      email: new FormControl(employeeRawValue.email),
      phoneNumber: new FormControl(employeeRawValue.phoneNumber),
      hireDate: new FormControl(employeeRawValue.hireDate),
      salary: new FormControl(employeeRawValue.salary),
      commissionPct: new FormControl(employeeRawValue.commissionPct),
      job: new FormControl(employeeRawValue.job),
      manager: new FormControl(employeeRawValue.manager),
      department: new FormControl(employeeRawValue.department),
    });
  }

  getEmployee(form: EmployeeFormGroup): IEmployee | NewEmployee {
    return this.convertEmployeeRawValueToEmployee(form.getRawValue() as EmployeeFormRawValue | NewEmployeeFormRawValue);
  }

  resetForm(form: EmployeeFormGroup, employee: EmployeeFormGroupInput): void {
    const employeeRawValue = this.convertEmployeeToEmployeeRawValue({ ...this.getFormDefaults(), ...employee });
    form.reset(
      {
        ...employeeRawValue,
        employeeId: { value: employeeRawValue.employeeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmployeeFormDefaults {
    const currentTime = dayjs();

    return {
      employeeId: null,
      hireDate: currentTime,
    };
  }

  private convertEmployeeRawValueToEmployee(rawEmployee: EmployeeFormRawValue | NewEmployeeFormRawValue): IEmployee | NewEmployee {
    return {
      ...rawEmployee,
      hireDate: dayjs(rawEmployee.hireDate, DATE_TIME_FORMAT),
    };
  }

  private convertEmployeeToEmployeeRawValue(
    employee: IEmployee | (Partial<NewEmployee> & EmployeeFormDefaults),
  ): EmployeeFormRawValue | PartialWithRequiredKeyOf<NewEmployeeFormRawValue> {
    return {
      ...employee,
      hireDate: employee.hireDate ? employee.hireDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
