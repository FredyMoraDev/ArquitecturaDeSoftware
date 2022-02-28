import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisComponentesComponent } from './mis-componentes.component';

describe('MisComponentesComponent', () => {
  let component: MisComponentesComponent;
  let fixture: ComponentFixture<MisComponentesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MisComponentesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MisComponentesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
