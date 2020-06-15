import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AlreadyVolunteerComponent } from './already-volunteer.component';

describe('AlreadyVolunteerComponent', () => {
  let component: AlreadyVolunteerComponent;
  let fixture: ComponentFixture<AlreadyVolunteerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlreadyVolunteerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlreadyVolunteerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
