import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AnimalsListComposant } from './animals-list-composant';

describe('AnimalsListComposant', () => {
  let component: AnimalsListComposant;
  let fixture: ComponentFixture<AnimalsListComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnimalsListComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(AnimalsListComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
