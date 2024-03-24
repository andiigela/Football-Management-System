import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginstatusComponent } from './loginstatus.component';

describe('LoginstatusComponent', () => {
  let component: LoginstatusComponent;
  let fixture: ComponentFixture<LoginstatusComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginstatusComponent]
    });
    fixture = TestBed.createComponent(LoginstatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
