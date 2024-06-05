import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMatchEventComponent } from './create-match-event.component';

describe('CreateMatchEventComponent', () => {
  let component: CreateMatchEventComponent;
  let fixture: ComponentFixture<CreateMatchEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateMatchEventComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateMatchEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
