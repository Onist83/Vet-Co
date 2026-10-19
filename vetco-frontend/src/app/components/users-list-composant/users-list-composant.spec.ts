import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UsersListComposant } from './users-list-composant';

describe('UsersListComposant', () => {
  let component: UsersListComposant;
  let fixture: ComponentFixture<UsersListComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsersListComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(UsersListComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
