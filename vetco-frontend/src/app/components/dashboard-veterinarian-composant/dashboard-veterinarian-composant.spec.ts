import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardVeterinarianComposant } from './dashboard-veterinarian-composant';

describe('DashboardVeterinarianComposant', () => {
  let component: DashboardVeterinarianComposant;
  let fixture: ComponentFixture<DashboardVeterinarianComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardVeterinarianComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardVeterinarianComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
