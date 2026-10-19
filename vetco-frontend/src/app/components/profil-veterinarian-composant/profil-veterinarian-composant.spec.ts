import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfilVeterinarianComposant } from './profil-veterinarian-composant';

describe('ProfilVeterinarianComposant', () => {
  let component: ProfilVeterinarianComposant;
  let fixture: ComponentFixture<ProfilVeterinarianComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilVeterinarianComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfilVeterinarianComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
