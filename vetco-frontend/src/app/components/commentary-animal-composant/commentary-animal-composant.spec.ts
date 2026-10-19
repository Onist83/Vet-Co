import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CommentaryAnimalComposant } from './commentary-animal-composant';

describe('CommentaryAnimalComposant', () => {
  let component: CommentaryAnimalComposant;
  let fixture: ComponentFixture<CommentaryAnimalComposant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommentaryAnimalComposant],
    }).compileComponents();

    fixture = TestBed.createComponent(CommentaryAnimalComposant);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
