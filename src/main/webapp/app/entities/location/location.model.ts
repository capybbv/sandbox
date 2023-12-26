import { IDepartment } from 'app/entities/department/department.model';
import { ICountry } from 'app/entities/country/country.model';

export interface ILocation {
  locationId: number;
  streetAddress?: string | null;
  postalCode?: string | null;
  city?: string | null;
  stateProvince?: string | null;
  departments?: IDepartment[] | null;
  country?: ICountry | null;
}

export type NewLocation = Omit<ILocation, 'locationId'> & { locationId: null };
