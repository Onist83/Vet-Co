import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserComposant } from './user-composant';

describe('UserComposant', () => {
  let component: UserComposant;
  let fixture: ComponentFixture<UserComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(UserComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
