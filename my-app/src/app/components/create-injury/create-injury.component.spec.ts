import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateInjuryComponent } from './create-injury.component';

describe('CreateInjuryComponent', () => {
  let component: CreateInjuryComponent;
  let fixture: ComponentFixture<CreateInjuryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateInjuryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateInjuryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
