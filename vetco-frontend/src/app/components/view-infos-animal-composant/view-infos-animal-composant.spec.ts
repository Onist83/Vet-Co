import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ViewInfosAnimalComposant } from './view-infos-animal-composant';

describe('ViewInfosAnimalComposant', () => {
  let component: ViewInfosAnimalComposant;
  let fixture: ComponentFixture<ViewInfosAnimalComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewInfosAnimalComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewInfosAnimalComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
