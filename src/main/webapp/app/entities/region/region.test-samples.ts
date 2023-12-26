import { IRegion, NewRegion } from './region.model';

export const sampleWithRequiredData: IRegion = {
  regionId: 25606,
};

export const sampleWithPartialData: IRegion = {
  regionId: 5835,
};

export const sampleWithFullData: IRegion = {
  regionId: 13937,
  regionName: 'quizzically landscape',
};

export const sampleWithNewData: NewRegion = {
  regionId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
