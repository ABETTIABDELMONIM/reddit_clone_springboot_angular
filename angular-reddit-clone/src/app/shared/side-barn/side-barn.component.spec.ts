import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideBarnComponent } from './side-barn.component';

describe('SideBarnComponent', () => {
  let component: SideBarnComponent;
  let fixture: ComponentFixture<SideBarnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SideBarnComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SideBarnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
