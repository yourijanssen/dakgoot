import { ComponentFixture, TestBed } from '@angular/core/testing';
import {HouseListComponent} from "../house-list-component/house-list.component";


describe('HouseDetailComponentComponent', () => {
  let component: HouseListComponent;
  let fixture: ComponentFixture<HouseListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HouseListComponent]
    });
    fixture = TestBed.createComponent(HouseListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
