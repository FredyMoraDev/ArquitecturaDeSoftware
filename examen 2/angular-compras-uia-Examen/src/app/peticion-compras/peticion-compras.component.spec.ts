import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeticionComprasComponent } from './peticion-compras.component';

describe('PeticionComprasComponent', () => {
  let component: PeticionComprasComponent;
  let fixture: ComponentFixture<PeticionComprasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PeticionComprasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PeticionComprasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
