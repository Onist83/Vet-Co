import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardAdminComposant } from './dashboard-admin-composant';

describe('DashboardAdminComposant', () => {
  let component: DashboardAdminComposant;
  let fixture: ComponentFixture<DashboardAdminComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardAdminComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardAdminComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
