import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InjuriesListComponent } from './injuries-list.component';

describe('InjuriesListComponent', () => {
  let component: InjuriesListComponent;
  let fixture: ComponentFixture<InjuriesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InjuriesListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InjuriesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
