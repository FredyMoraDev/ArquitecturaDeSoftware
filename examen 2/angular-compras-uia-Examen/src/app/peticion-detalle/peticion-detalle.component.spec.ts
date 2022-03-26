import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeticionDetalleComponent } from './peticion-detalle.component';

describe('PeticionDetalleComponent', () => {
  let component: PeticionDetalleComponent;
  let fixture: ComponentFixture<PeticionDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PeticionDetalleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PeticionDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
