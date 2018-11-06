import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IngresarVehiculoComponent } from './ingresar-vehiculo.component';

describe('IngresarVehiculoComponent', () => {
  let component: IngresarVehiculoComponent;
  let fixture: ComponentFixture<IngresarVehiculoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IngresarVehiculoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IngresarVehiculoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
