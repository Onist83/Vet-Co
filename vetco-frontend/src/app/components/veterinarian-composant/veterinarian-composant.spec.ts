import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VeterinarianComposant } from './veterinarian-composant';

describe('VeterinarianComposant', () => {
  let component: VeterinarianComposant;
  let fixture: ComponentFixture<VeterinarianComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VeterinarianComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(VeterinarianComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
