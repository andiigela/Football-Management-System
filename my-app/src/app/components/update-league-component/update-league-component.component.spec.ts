import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateLeagueComponentComponent } from './update-league-component.component';

describe('UpdateLeagueComponentComponent', () => {
  let component: UpdateLeagueComponentComponent;
  let fixture: ComponentFixture<UpdateLeagueComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateLeagueComponentComponent]
    });
    fixture = TestBed.createComponent(UpdateLeagueComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
