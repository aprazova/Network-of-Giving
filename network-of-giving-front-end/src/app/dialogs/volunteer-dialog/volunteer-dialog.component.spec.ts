import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VolunteerDialogComponent } from './volunteer-dialog.component';

describe('VolunteerDialogComponent', () => {
  let component: VolunteerDialogComponent;
  let fixture: ComponentFixture<VolunteerDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VolunteerDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VolunteerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
