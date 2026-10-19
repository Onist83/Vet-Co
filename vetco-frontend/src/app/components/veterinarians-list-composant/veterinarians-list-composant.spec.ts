import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VeterinariansListComposant } from './veterinarians-list-composant';

describe('VeterinariansListComposant', () => {
  let component: VeterinariansListComposant;
  let fixture: ComponentFixture<VeterinariansListComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VeterinariansListComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(VeterinariansListComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
