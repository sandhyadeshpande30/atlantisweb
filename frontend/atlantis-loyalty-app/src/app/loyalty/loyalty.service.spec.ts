import { TestBed } from '@angular/core/testing';

import { LoyaltyService } from './loyalty.service';

describe('DataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LoyaltyService = TestBed.get(LoyaltyService);
    expect(service).toBeTruthy();
  });
});
