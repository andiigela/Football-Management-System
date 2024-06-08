import { TestBed } from '@angular/core/testing';

import { StandingService } from './standing.service';

describe('StandingService', () => {
  let service: StandingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StandingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
