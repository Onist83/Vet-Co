import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfilUserComposant } from './profil-user-composant';

describe('ProfilUserComposant', () => {
  let component: ProfilUserComposant;
  let fixture: ComponentFixture<ProfilUserComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilUserComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfilUserComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
