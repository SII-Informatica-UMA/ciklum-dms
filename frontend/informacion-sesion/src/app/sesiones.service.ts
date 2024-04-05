import { Injectable } from '@angular/core';
import {Sesion } from './entities/sesion'

@Injectable({
  providedIn: 'root'
})
export class SesionesService {
  private sesiones: Sesion [] = [
    {idPlan: 1, inicio:new Date('2023-12-21 08:00:00') ,fin:new Date('2023-12-31 08:00:00'),trabajoRealizado:"Circuito 1",multimedia:[],descripcion:"Realiza el circuito especializado en espalda y brazos",presencial:true,datosSalud:[],id:1 },
    {idPlan: 1, inicio:new Date('2023-11-21 08:00:00') ,fin:new Date('2024-02-03 08:00:00'),trabajoRealizado:"Circuito 2",multimedia:[],descripcion:"Realiza el circuito especializado en glúteo",presencial:false,datosSalud:[],id:2},
    {idPlan: 2, inicio:new Date('2024-02-01 08:00:00') ,fin:new Date('2024-02-21 08:00:00'),trabajoRealizado:"Circuito 3",multimedia:[],descripcion:"Realiza el circuito general",presencial:true,datosSalud:[],id:3},
  ];

  constructor() { }

  getSesiones(): Sesion [] {
    return this.sesiones;
  }

  addSesion(sesion: Sesion) {
    sesion.id = Math.max(...this.sesiones.map(c => c.id)) + 1;
    this.sesiones.push(sesion);
  }

  editarSesion(sesion: Sesion) {
    let indice = this.sesiones.findIndex(c => c.id == sesion.id);
    this.sesiones[indice] = sesion;
  }

  eliminarSesion(id: number) {
    let indice = this.sesiones.findIndex(c => c.id == id);
    this.sesiones.splice(indice, 1);
  }
}
