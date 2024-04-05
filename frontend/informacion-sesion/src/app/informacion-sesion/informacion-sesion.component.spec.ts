import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InformacionSesionComponent } from './informacion-sesion.component';

describe('InformacionSesionComponent', () => {
  let component: InformacionSesionComponent;
  let fixture: ComponentFixture<InformacionSesionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InformacionSesionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InformacionSesionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
