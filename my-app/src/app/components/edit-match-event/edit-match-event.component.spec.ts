import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditMatchEventComponent } from './edit-match-event.component';

describe('EditMatchEventComponent', () => {
  let component: EditMatchEventComponent;
  let fixture: ComponentFixture<EditMatchEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditMatchEventComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditMatchEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
