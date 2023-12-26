import { ILocation } from 'app/entities/location/location.model';
import { IRegion } from 'app/entities/region/region.model';

export interface ICountry {
  countryId: string;
  countryName?: string | null;
  locations?: ILocation[] | null;
  region?: IRegion | null;
}

export type NewCountry = Omit<ICountry, 'countryId'> & { countryId: null };
