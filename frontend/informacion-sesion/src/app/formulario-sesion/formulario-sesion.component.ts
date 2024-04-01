import { Component } from '@angular/core';
import  {Sesion} from '../sesion';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-formulario-sesion',
  templateUrl: './formulario-sesion.component.html',
  styleUrls: ['./formulario-sesion.component.css']
})
export class FormularioSesionComponent {
  accion!: "Añadir" | "Editar";
  sesion: Sesion = {id: 0, inicio: undefined , fin: undefined, idPlan: 0, trabajoRealizado: '', multimedia: [],
                    descripcion: '', datosSalud: [], presencial: false};

  constructor(public modal: NgbActiveModal) { }

  guardarContacto(): void {
    this.modal.close(this.sesion);
  }

}
