import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfilVeterianComposant } from './profil-veterian-composant';

describe('ProfilVeterianComposant', () => {
  let component: ProfilVeterianComposant;
  let fixture: ComponentFixture<ProfilVeterianComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilVeterianComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfilVeterianComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
