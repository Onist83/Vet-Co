import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AnimalComposant } from './animal-composant';

describe('AnimalComposant', () => {
  let component: AnimalComposant;
  let fixture: ComponentFixture<AnimalComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnimalComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(AnimalComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
