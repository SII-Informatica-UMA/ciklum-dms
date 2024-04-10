import { Component, OnInit } from '@angular/core';
import {Sesion } from '../entities/sesion';
import {SesionesService } from '../services/sesiones.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioSesionComponent} from '../formulario-sesion/formulario-sesion.component'
import { DetalleSesionComponent } from '../detalle-sesion/detalle-sesion.component';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [DetalleSesionComponent,CommonModule],
  selector: 'app-root',
  templateUrl: './informacion-sesion.html',
  styleUrls: ['./informacion-sesion.css']
})
export class AppComponent implements OnInit {
  sesiones: Sesion [] = [];
  sesionElegida?: Sesion;

  constructor(private sesionesService: SesionesService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.sesiones = this.sesionesService.getSesiones();
  }

  elegirSesion(sesion: Sesion): void {
    this.sesionElegida = sesion;
  }

  aniadirSesion(): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.sesion = {id: 0, nombre: '', apellidos: '', email: '', telefono: ''};
    ref.result.then((sesion: Sesion) => {
      this.sesionesService.addSesion(sesion);
      this.sesiones = this.sesionesService.getSesiones();
    }, (reason) => {});

  }
  sesionEditada(sesion: Sesion): void {
    this.sesionesService.editarSesion(sesion);
    this.sesiones = this.sesionesService.getSesiones();
    this.sesionElegida = this.sesiones.find(c => c.id == sesion.id);
  }

  eliminarSesion(id: number): void {
    this.sesionesService.eliminarSesion(id);
    this.sesiones = this.sesionesService.getSesiones();
    this.sesionElegida = undefined;
  }
}
