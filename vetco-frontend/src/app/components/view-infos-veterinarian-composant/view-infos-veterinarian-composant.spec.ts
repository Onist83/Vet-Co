import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ViewInfosVeterinarianComposant } from './view-infos-veterinarian-composant';

describe('ViewInfosVeterinarianComposant', () => {
  let component: ViewInfosVeterinarianComposant;
  let fixture: ComponentFixture<ViewInfosVeterinarianComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewInfosVeterinarianComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewInfosVeterinarianComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
