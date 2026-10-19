import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardUserComposant } from './dashboard-user-composant';

describe('DashboardUserComposant', () => {
  let component: DashboardUserComposant;
  let fixture: ComponentFixture<DashboardUserComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardUserComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardUserComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
