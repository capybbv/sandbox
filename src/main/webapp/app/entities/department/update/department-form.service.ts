import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDepartment, NewDepartment } from '../department.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { departmentId: unknown }> = Partial<Omit<T, 'departmentId'>> & { departmentId: T['departmentId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDepartment for edit and NewDepartmentFormGroupInput for create.
 */
type DepartmentFormGroupInput = IDepartment | PartialWithRequiredKeyOf<NewDepartment>;

type DepartmentFormDefaults = Pick<NewDepartment, 'departmentId'>;

type DepartmentFormGroupContent = {
  departmentId: FormControl<IDepartment['departmentId'] | NewDepartment['departmentId']>;
  departmentName: FormControl<IDepartment['departmentName']>;
  manager: FormControl<IDepartment['manager']>;
  location: FormControl<IDepartment['location']>;
};

export type DepartmentFormGroup = FormGroup<DepartmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DepartmentFormService {
  createDepartmentFormGroup(department: DepartmentFormGroupInput = { departmentId: null }): DepartmentFormGroup {
    const departmentRawValue = {
      ...this.getFormDefaults(),
      ...department,
    };
    return new FormGroup<DepartmentFormGroupContent>({
      departmentId: new FormControl(
        { value: departmentRawValue.departmentId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      departmentName: new FormControl(departmentRawValue.departmentName),
      manager: new FormControl(departmentRawValue.manager),
      location: new FormControl(departmentRawValue.location),
    });
  }

  getDepartment(form: DepartmentFormGroup): IDepartment | NewDepartment {
    return form.getRawValue() as IDepartment | NewDepartment;
  }

  resetForm(form: DepartmentFormGroup, department: DepartmentFormGroupInput): void {
    const departmentRawValue = { ...this.getFormDefaults(), ...department };
    form.reset(
      {
        ...departmentRawValue,
        departmentId: { value: departmentRawValue.departmentId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DepartmentFormDefaults {
    return {
      departmentId: null,
    };
  }
}
