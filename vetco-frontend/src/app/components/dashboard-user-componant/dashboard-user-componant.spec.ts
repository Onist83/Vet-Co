import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardUserComponant } from './dashboard-user-componant';

describe('DashboardUserComponant', () => {
  let component: DashboardUserComponant;
  let fixture: ComponentFixture<DashboardUserComponant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardUserComponant],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardUserComponant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
