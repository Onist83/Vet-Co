import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VeterianComposant } from './veterian-composant';

describe('VeterianComposant', () => {
  let component: VeterianComposant;
  let fixture: ComponentFixture<VeterianComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VeterianComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(VeterianComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
