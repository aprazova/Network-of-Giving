import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CharityEditComponent } from './charity-edit.component';

describe('CharityEditComponent', () => {
  let component: CharityEditComponent;
  let fixture: ComponentFixture<CharityEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CharityEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CharityEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
