import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILocation, NewLocation } from '../location.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { locationId: unknown }> = Partial<Omit<T, 'locationId'>> & { locationId: T['locationId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILocation for edit and NewLocationFormGroupInput for create.
 */
type LocationFormGroupInput = ILocation | PartialWithRequiredKeyOf<NewLocation>;

type LocationFormDefaults = Pick<NewLocation, 'locationId'>;

type LocationFormGroupContent = {
  locationId: FormControl<ILocation['locationId'] | NewLocation['locationId']>;
  streetAddress: FormControl<ILocation['streetAddress']>;
  postalCode: FormControl<ILocation['postalCode']>;
  city: FormControl<ILocation['city']>;
  stateProvince: FormControl<ILocation['stateProvince']>;
  country: FormControl<ILocation['country']>;
};

export type LocationFormGroup = FormGroup<LocationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LocationFormService {
  createLocationFormGroup(location: LocationFormGroupInput = { locationId: null }): LocationFormGroup {
    const locationRawValue = {
      ...this.getFormDefaults(),
      ...location,
    };
    return new FormGroup<LocationFormGroupContent>({
      locationId: new FormControl(
        { value: locationRawValue.locationId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      streetAddress: new FormControl(locationRawValue.streetAddress),
      postalCode: new FormControl(locationRawValue.postalCode),
      city: new FormControl(locationRawValue.city),
      stateProvince: new FormControl(locationRawValue.stateProvince),
      country: new FormControl(locationRawValue.country),
    });
  }

  getLocation(form: LocationFormGroup): ILocation | NewLocation {
    return form.getRawValue() as ILocation | NewLocation;
  }

  resetForm(form: LocationFormGroup, location: LocationFormGroupInput): void {
    const locationRawValue = { ...this.getFormDefaults(), ...location };
    form.reset(
      {
        ...locationRawValue,
        locationId: { value: locationRawValue.locationId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LocationFormDefaults {
    return {
      locationId: null,
    };
  }
}
