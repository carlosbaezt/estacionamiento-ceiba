import { TestBed } from '@angular/core/testing';

import { TrmService } from './trm.service';

describe('TrmService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TrmService = TestBed.get(TrmService);
    expect(service).toBeTruthy();
  });
});
