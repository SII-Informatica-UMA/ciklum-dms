import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { InformacionSesion } from './informacion-sesion.component';

describe('InformacionSesion', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        InformacionSesion
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(InformacionSesion);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'practica5'`, () => {
    const fixture = TestBed.createComponent(InformacionSesion);
    const app = fixture.componentInstance;
    expect(app.sesionElegida).toBeUndefined();
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(InformacionSesion);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.container h1')?.textContent).toContain('Lista de sesiones');
  });
});
