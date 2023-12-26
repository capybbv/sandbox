import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  locationId: 1662,
};

export const sampleWithPartialData: ILocation = {
  locationId: 6956,
  streetAddress: 'tussle victoriously',
  postalCode: 'wan courteous',
  stateProvince: 'brag up blissfully',
};

export const sampleWithFullData: ILocation = {
  locationId: 10779,
  streetAddress: 'whenever in-joke',
  postalCode: 'search plumber grotesque',
  city: 'Thornton',
  stateProvince: 'carelessly where travel',
};

export const sampleWithNewData: NewLocation = {
  locationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
