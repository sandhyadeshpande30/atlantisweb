import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PointsViewComponent } from './points-view.component';

describe('PointsViewComponent', () => {
  let component: PointsViewComponent;
  let fixture: ComponentFixture<PointsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PointsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PointsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
