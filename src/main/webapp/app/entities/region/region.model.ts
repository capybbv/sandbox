import { ICountry } from 'app/entities/country/country.model';

export interface IRegion {
  regionId: number;
  regionName?: string | null;
  countries?: ICountry[] | null;
}

export type NewRegion = Omit<IRegion, 'regionId'> & { regionId: null };
