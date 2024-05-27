import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSeasonComponent } from './edit-season.component';

describe('EditSeasonComponent', () => {
  let component: EditSeasonComponent;
  let fixture: ComponentFixture<EditSeasonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditSeasonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditSeasonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
