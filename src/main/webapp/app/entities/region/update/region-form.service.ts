import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRegion, NewRegion } from '../region.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { regionId: unknown }> = Partial<Omit<T, 'regionId'>> & { regionId: T['regionId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRegion for edit and NewRegionFormGroupInput for create.
 */
type RegionFormGroupInput = IRegion | PartialWithRequiredKeyOf<NewRegion>;

type RegionFormDefaults = Pick<NewRegion, 'regionId'>;

type RegionFormGroupContent = {
  regionId: FormControl<IRegion['regionId'] | NewRegion['regionId']>;
  regionName: FormControl<IRegion['regionName']>;
};

export type RegionFormGroup = FormGroup<RegionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RegionFormService {
  createRegionFormGroup(region: RegionFormGroupInput = { regionId: null }): RegionFormGroup {
    const regionRawValue = {
      ...this.getFormDefaults(),
      ...region,
    };
    return new FormGroup<RegionFormGroupContent>({
      regionId: new FormControl(
        { value: regionRawValue.regionId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      regionName: new FormControl(regionRawValue.regionName),
    });
  }

  getRegion(form: RegionFormGroup): IRegion | NewRegion {
    return form.getRawValue() as IRegion | NewRegion;
  }

  resetForm(form: RegionFormGroup, region: RegionFormGroupInput): void {
    const regionRawValue = { ...this.getFormDefaults(), ...region };
    form.reset(
      {
        ...regionRawValue,
        regionId: { value: regionRawValue.regionId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RegionFormDefaults {
    return {
      regionId: null,
    };
  }
}
