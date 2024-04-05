import { Routes } from '@angular/router';
import { PrincipalComponent } from './principal/principal.component';
import { InformacionSesionComponent } from './informacion-sesion/informacion-sesion.component';

export const routes: Routes = [
    {
        path: 'sesion',
        component: InformacionSesionComponent
    },{
        path: '',
        component: PrincipalComponent
      }
];
