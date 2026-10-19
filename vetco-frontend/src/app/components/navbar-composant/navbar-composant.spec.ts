import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NavbarComposant } from './navbar-composant';

describe('NavbarComposant', () => {
  let component: NavbarComposant;
  let fixture: ComponentFixture<NavbarComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(NavbarComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
