import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComposant } from './login-composant';

describe('LoginComposant', () => {
  let component: LoginComposant;
  let fixture: ComponentFixture<LoginComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
