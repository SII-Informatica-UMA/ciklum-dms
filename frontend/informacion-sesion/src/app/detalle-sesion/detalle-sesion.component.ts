import { Component, Input, Output, EventEmitter } from '@angular/core';
import {Sesion } from '../entities/sesion';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioSesionComponent} from '../formulario-sesion/formulario-sesion.component'
import { SesionesService } from '../services/sesiones.service';
import { DatePipe } from '@angular/common';
import { UsuariosService } from '../services/usuarios.service';
import { Rol } from '../entities/login';
import { InformacionSesion } from '../informacion-sesion/informacion-sesion.component';

@Component({
  selector: 'app-detalle-sesion',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './detalle-sesion.component.html',
  styleUrls: ['./detalle-sesion.component.css']
})
export class DetalleSesionComponent {
  @Input() sesion?: Sesion;
  @Output() sesionEditada = new EventEmitter<Sesion>();
  @Output() sesionEliminada = new EventEmitter<number>();

  constructor(private sesionesService: SesionesService, private usuariosService:UsuariosService, private modalService: NgbModal) { }

  editarSesion(): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.sesion = {...this.sesion};
    ref.result.then((sesion: Sesion) => {
      this.sesionEditada.emit(sesion);
    }, (reason) => {});
  }

  eliminarSesion(): void {
    this.sesionEliminada.emit(this.sesion?.id);
  }

  isCliente(): boolean{
    //return this.usuariosService.rolCentro?.rol == Rol.CLIENTE;
    return true;
  }

  isEntrenador(): boolean{
    return this.usuariosService.rolCentro?.rol == Rol.ENTRENADOR;
  }
}
