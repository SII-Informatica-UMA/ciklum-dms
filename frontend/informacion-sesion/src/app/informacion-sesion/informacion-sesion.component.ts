import { Component, OnInit } from '@angular/core';
import {Sesion } from '../entities/sesion'
import {SesionesService } from '../services/sesiones.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioSesionComponent} from '../formulario-sesion/formulario-sesion.component'
import { DetalleSesionComponent } from '../detalle-sesion/detalle-sesion.component';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Usuario } from '../entities/usuario';
import { UsuariosService } from '../services/usuarios.service';
import { Plan } from '../entities/plan';

@Component({
  standalone: true,
  imports: [DetalleSesionComponent,RouterLink,FormsModule,CommonModule],
  selector: 'informacion-sesion',
  templateUrl: './informacion-sesion.component.html',
  styleUrls: ['./informacion-sesion.component.css']
})
export class InformacionSesion implements OnInit {
  sesiones: Sesion [] = [];
  planes: Plan[] = [];
  indice: number = 0;
  sesionElegida?: Sesion;
  constructor(private sesionesService: SesionesService, private usuariosService: UsuariosService, private modalService: NgbModal) { 
    this.actualizarPlanes();
  }

  ngOnInit(): void {
    this.actualizarSesiones();
  }

  actualizarPlanes(){
    this.planes = this.sesionesService.getPlanes(this.usuariosService.getUsuarioSesion()?.id);
  }

  actualizarSesiones() {
    this.sesionesService.getSesiones(this.planes[this.indice].id).subscribe(sesiones => {
      this.sesiones = sesiones;
    });
  }

  elegirSesion(sesion: Sesion): void {
    this.sesionElegida = sesion;
  }

  aniadirSesion(): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.sesion = {idPlan: 0,trabajoRealizado:"",multimedia:[],descripcion:"",presencial:true,datosSalud:[],id:0};
    ref.result.then((sesion: Sesion) => {
      this.sesionesService.addSesion(sesion).subscribe(sesion =>{
        this.actualizarSesiones();
      })
    }, (reason) => {});

  }
  sesionEditada(sesion: Sesion): void {
    this.sesionesService.editarSesion(sesion).subscribe(() => {
      this.actualizarSesiones();
    });
  }

  eliminarSesion(id: number): void {
    this.sesionesService.eliminarSesion(id).subscribe(() => {
      this.actualizarSesiones();
    });
  }

  
}
