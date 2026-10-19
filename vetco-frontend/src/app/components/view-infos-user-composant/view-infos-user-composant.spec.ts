import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ViewInfosUserComposant } from './view-infos-user-composant';

describe('ViewInfosUserComposant', () => {
  let component: ViewInfosUserComposant;
  let fixture: ComponentFixture<ViewInfosUserComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewInfosUserComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewInfosUserComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
