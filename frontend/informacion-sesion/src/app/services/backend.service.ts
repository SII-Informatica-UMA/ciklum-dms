import { Injectable } from "@angular/core";
import { Observable, map, of } from "rxjs";
import { Usuario } from "../entities/usuario";
import { HttpClient } from "@angular/common/http";
import { BACKEND_URI } from "../config/config";
import { JwtResponse } from "../entities/login";
import { Sesion } from "../entities/sesion";
import { AsignacionEntrenamiento } from "../entities/asignacionEntrenamiento";
import { Cliente } from "../entities/cliente";
import { Centro } from "../entities/centro";
import { Entrenador } from "../entities/entrenador";

// Este servicio usa el backend real

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private httpClient: HttpClient) {}

  getUsuarios(): Observable<Usuario[]> {
    return this.httpClient.get<Usuario[]>(BACKEND_URI + '/usuario');
  }

  postUsuario(usuario: Usuario): Observable<Usuario> {
    return this.httpClient.post<Usuario>(BACKEND_URI + '/usuario', usuario);
  }

  putUsuario(usuario: Usuario): Observable<Usuario> {
    return this.httpClient.put<Usuario>(BACKEND_URI + '/usuario/' + usuario.id, usuario);
  }

  deleteUsuario(id: number): Observable<void> {
    return this.httpClient.delete<void>(BACKEND_URI + '/usuario/' + id);
  }

  getUsuario(id: number): Observable<Usuario> {
    return this.httpClient.get<Usuario>(BACKEND_URI + '/usuario/' + id);
  }

  login(email: string, password: string): Observable<string> {
    return this.httpClient.post<JwtResponse>(BACKEND_URI + '/login', {email: email, password: password})
      .pipe(map(jwtResponse => jwtResponse.jwt));
  }

  forgottenPassword(email: string): Observable<void> {
    return this.httpClient.post<void>(BACKEND_URI + '/forgottenpassword', {email: email});
  }

  resetPassword(token: string, password: string): Observable<void> {
    return this.httpClient.post<void>(BACKEND_URI + '/passwordreset', {token: token, password: password});
  }

  getSesiones(idPlan: number):Observable<Sesion[]>{
    return this.httpClient.get<Sesion[]>(BACKEND_URI + '/sesion?plan=' + idPlan);
  }

  getSesion(idSesion: number): Observable<Sesion>{
    return this.httpClient.get<Sesion>(BACKEND_URI + '/sesion/' + idSesion);
  }

  postSesion(sesion: Sesion): Observable<Sesion>{
    return this.httpClient.post<Sesion>(BACKEND_URI + '/sesion?plan=' + sesion.idPlan, sesion);
  }

  putSesion(sesion:Sesion):Observable<Sesion>{
    return this.httpClient.put<Sesion>(BACKEND_URI + '/sesion/' + sesion.id, sesion);

  }

  deleteSesion(id:number): Observable<void>{
    return this.httpClient.delete<void>(BACKEND_URI + '/sesion/' + id);
  }

  getEntrenaE(idEntrenador:number): Observable<AsignacionEntrenamiento[]>{
    return this.httpClient.get<AsignacionEntrenamiento[]>(BACKEND_URI + '/entrena?entrenador=' + idEntrenador);
  }

  getEntrenaC(idCliente:number): Observable<AsignacionEntrenamiento[]>{
    return this.httpClient.get<AsignacionEntrenamiento[]>(BACKEND_URI + '/entrena?cliente=' + idCliente);
  }

  getCentros(): Observable<Centro[]>{
    return this.httpClient.get<Centro[]>(BACKEND_URI + '/centro');
  }

  getClientes(idCentro:number): Observable<Cliente[]> {
    return this.httpClient.get<Cliente[]>(BACKEND_URI + '/cliente?centro=' + idCentro);
  }

  getEntrenadores(idCentro:number): Observable<Entrenador[]>{
    return this.httpClient.get<Entrenador[]>(BACKEND_URI + '/entrenador?centro=' + idCentro);
  }
}
