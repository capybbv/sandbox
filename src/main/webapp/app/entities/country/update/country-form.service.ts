import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICountry, NewCountry } from '../country.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { countryId: unknown }> = Partial<Omit<T, 'countryId'>> & { countryId: T['countryId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICountry for edit and NewCountryFormGroupInput for create.
 */
type CountryFormGroupInput = ICountry | PartialWithRequiredKeyOf<NewCountry>;

type CountryFormDefaults = Pick<NewCountry, 'countryId'>;

type CountryFormGroupContent = {
  countryId: FormControl<ICountry['countryId'] | NewCountry['countryId']>;
  countryName: FormControl<ICountry['countryName']>;
  region: FormControl<ICountry['region']>;
};

export type CountryFormGroup = FormGroup<CountryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CountryFormService {
  createCountryFormGroup(country: CountryFormGroupInput = { countryId: null }): CountryFormGroup {
    const countryRawValue = {
      ...this.getFormDefaults(),
      ...country,
    };
    return new FormGroup<CountryFormGroupContent>({
      countryId: new FormControl(
        { value: countryRawValue.countryId, disabled: countryRawValue.countryId !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      countryName: new FormControl(countryRawValue.countryName),
      region: new FormControl(countryRawValue.region),
    });
  }

  getCountry(form: CountryFormGroup): ICountry | NewCountry {
    return form.getRawValue() as ICountry | NewCountry;
  }

  resetForm(form: CountryFormGroup, country: CountryFormGroupInput): void {
    const countryRawValue = { ...this.getFormDefaults(), ...country };
    form.reset(
      {
        ...countryRawValue,
        countryId: { value: countryRawValue.countryId, disabled: countryRawValue.countryId !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CountryFormDefaults {
    return {
      countryId: null,
    };
  }
}
