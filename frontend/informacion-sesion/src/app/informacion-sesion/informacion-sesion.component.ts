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
import { Rol, UsuarioSesion } from '../entities/login';

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
  }

  ngOnInit(): void {
    this.indice = 0;
    this.actualizarPlanes();
  }

  isCliente(): boolean{
    return this.usuariosService.rolCentro?.rol == Rol.CLIENTE;
    //return true;
  }

  isEntrenador(): boolean{
    return this.usuariosService.rolCentro?.rol == Rol.ENTRENADOR;
  }

  actualizarPlanes(){
    this.planes = [];
    console.log(this.usuariosService.getUsuarioSesion()?.id);
    let usuario: UsuarioSesion |undefined = this.usuariosService.getUsuarioSesion();
    if(usuario != undefined){
      this.sesionesService.getAsignaciones(usuario.id).subscribe(asignaciones => {
        asignaciones.forEach(asig => {
          console.log("asignacion id cliente " + asig.idCliente);
          asig.planes.forEach(plan =>{
            console.log("Plan " + plan.id);
            this.planes.push(plan);
          })
        })
        this.actualizarSesiones();
      })
    }
        //this.actualizarSesiones();
  }

  actualizarSesiones(){
    let plan = this.planes[this.indice]
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
    ref.componentInstance.sesion = {idPlan: this.planes[this.indice].id,trabajoRealizado:"",multimedia:[],descripcion:"",presencial:true,datosSalud:[],id:0};
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
    this.sesionElegida = undefined;
  }

  siguientePlan(){
    if(this.indice+1 < this.planes.length){
      console.log("Tamaño planes: " + this.planes.length);
      this.indice++;
      this.actualizarSesiones();
    }
  }

  anteriorPlan(){
    if(this.indice-1 >= 0){
      this.indice--;
      this.actualizarSesiones();
    }
  }

  
}
