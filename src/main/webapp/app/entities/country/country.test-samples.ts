import { ICountry, NewCountry } from './country.model';

export const sampleWithRequiredData: ICountry = {
  countryId: 'f0ff630c-63a8-4449-b3f8-e988aa537a5f',
};

export const sampleWithPartialData: ICountry = {
  countryId: '6a217f3e-e6a0-4219-9d49-b9fdd6b60744',
  countryName: 'heartwood puzzling',
};

export const sampleWithFullData: ICountry = {
  countryId: 'd6675f35-8073-425c-b686-689f1d817ba5',
  countryName: 'live aspire',
};

export const sampleWithNewData: NewCountry = {
  countryId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
