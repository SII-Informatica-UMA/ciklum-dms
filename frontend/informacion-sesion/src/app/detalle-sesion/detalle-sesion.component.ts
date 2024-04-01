import { Component, Input, Output, EventEmitter } from '@angular/core';
import {Sesion } from '../sesion';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioContactoComponent} from '../formulario-contacto/formulario-contacto.component'
import { SesionesService } from '../sesiones.service';

@Component({
  selector: 'app-detalle-contacto',
  templateUrl: './detalle-sesion.component.html',
  styleUrls: ['./detalle-sesion.component.css']
})
export class DetalleSesionComponent {
  @Input() sesion?: Sesion;
  @Output() sesionEditado = new EventEmitter<Sesion>();
  @Output() sesionEliminado = new EventEmitter<number>();

  constructor(private sesionesService: SesionesService, private modalService: NgbModal) { }

  editarSesion(): void {
    let ref = this.modalService.open(FormularioContactoComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.sesion = {...this.sesion};
    ref.result.then((sesion: Sesion) => {
      this.sesionEditado.emit(sesion);
    }, (reason) => {});
  }

  eliminarSesion(): void {
    this.sesionEliminado.emit(this.sesion?.id);
  }
}
