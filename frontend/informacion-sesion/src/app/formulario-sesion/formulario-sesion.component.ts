import { Component } from '@angular/core';
import  {Sesion} from '../entities/sesion';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  standalone:true,
  imports:[CommonModule,FormsModule],
  selector: 'app-formulario-sesion',
  templateUrl: './formulario-sesion.component.html',
  styleUrls: ['./formulario-sesion.component.css']
})
export class FormularioSesionComponent {
  accion!: "Añadir" | "Editar";
  multimedia!: string 
  sesion: Sesion = {id: 0, inicio: undefined , fin: undefined, idPlan: 0, trabajoRealizado: '', multimedia: [],
                    descripcion: '', datosSalud: [], presencial: false};

  constructor(public modal: NgbActiveModal) { }

  guardarSesion(): void {
    this.sesion.multimedia = this.multimedia.split(" ");
    this.modal.close(this.sesion);

  }


}