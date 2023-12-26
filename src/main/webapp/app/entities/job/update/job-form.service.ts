import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IJob, NewJob } from '../job.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { jobId: unknown }> = Partial<Omit<T, 'jobId'>> & { jobId: T['jobId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJob for edit and NewJobFormGroupInput for create.
 */
type JobFormGroupInput = IJob | PartialWithRequiredKeyOf<NewJob>;

type JobFormDefaults = Pick<NewJob, 'jobId'>;

type JobFormGroupContent = {
  jobId: FormControl<IJob['jobId'] | NewJob['jobId']>;
  jobTitle: FormControl<IJob['jobTitle']>;
  minSalary: FormControl<IJob['minSalary']>;
  maxSalary: FormControl<IJob['maxSalary']>;
};

export type JobFormGroup = FormGroup<JobFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JobFormService {
  createJobFormGroup(job: JobFormGroupInput = { jobId: null }): JobFormGroup {
    const jobRawValue = {
      ...this.getFormDefaults(),
      ...job,
    };
    return new FormGroup<JobFormGroupContent>({
      jobId: new FormControl(
        { value: jobRawValue.jobId, disabled: jobRawValue.jobId !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      jobTitle: new FormControl(jobRawValue.jobTitle),
      minSalary: new FormControl(jobRawValue.minSalary),
      maxSalary: new FormControl(jobRawValue.maxSalary),
    });
  }

  getJob(form: JobFormGroup): IJob | NewJob {
    return form.getRawValue() as IJob | NewJob;
  }

  resetForm(form: JobFormGroup, job: JobFormGroupInput): void {
    const jobRawValue = { ...this.getFormDefaults(), ...job };
    form.reset(
      {
        ...jobRawValue,
        jobId: { value: jobRawValue.jobId, disabled: jobRawValue.jobId !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): JobFormDefaults {
    return {
      jobId: null,
    };
  }
}
