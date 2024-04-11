import { Injectable } from '@angular/core';
import {Sesion } from '../entities/sesion'
import { Observable } from 'rxjs';
import { BackendService } from './backend.service';
import { Plan } from '../entities/plan';

@Injectable({
  providedIn: 'root'
})
export class SesionesService {
  private sesiones: Sesion [] = [
    {idPlan: 1, inicio:new Date('2023-12-21 08:00:00') ,fin:new Date('2023-12-31 08:00:00'),trabajoRealizado:"Circuito 1",multimedia:[],descripcion:"Realiza el circuito especializado en espalda y brazos",presencial:true,datosSalud:[],id:1 },
    {idPlan: 1, inicio:new Date('2023-11-21 08:00:00') ,fin:new Date('2024-02-03 08:00:00'),trabajoRealizado:"Circuito 2",multimedia:[],descripcion:"Realiza el circuito especializado en glúteo",presencial:false,datosSalud:[],id:2},
    {idPlan: 2, inicio:new Date('2024-02-01 08:00:00') ,fin:new Date('2024-02-21 08:00:00'),trabajoRealizado:"Circuito 3",multimedia:[],descripcion:"Realiza el circuito general",presencial:true,datosSalud:[],id:3},
  ];

  constructor(private backend: BackendService) { }

  getSesiones(idPlan: number): Observable<Sesion []> {
    return this.backend.getSesiones(idPlan);
  }

  addSesion(sesion: Sesion): Observable<Sesion> {
    sesion.id = Math.max(...this.sesiones.map(c => c.id)) + 1;
    return this.backend.postSesion(sesion);
  }

  editarSesion(sesion: Sesion): Observable<Sesion> {
    return this.backend.putSesion(sesion);
  }

  eliminarSesion(id: number): Observable<void> {
    return this.backend.deleteSesion(id);
  }

  getPlanes(idUsuario: number|undefined): Plan[]{
    if(idUsuario === undefined)
      return [];
    let planes = new Set<Plan>();
    this.backend.getEntrenaC(idUsuario).subscribe(asignaciones => {
      asignaciones.map(asig => {
        asig.planes.map(plan => planes.add(plan))
      })
    })
    return Array.from(planes);
  }
}
