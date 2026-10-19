import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterAnimalComposant } from './register-animal-composant';

describe('RegisterAnimalComposant', () => {
  let component: RegisterAnimalComposant;
  let fixture: ComponentFixture<RegisterAnimalComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegisterAnimalComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterAnimalComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
