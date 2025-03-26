import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeownerDashboardComponent } from './homeowner-dashboard.component';

describe('HomeownerDashboardComponent', () => {
  let component: HomeownerDashboardComponent;
  let fixture: ComponentFixture<HomeownerDashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeownerDashboardComponent]
    });
    fixture = TestBed.createComponent(HomeownerDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
