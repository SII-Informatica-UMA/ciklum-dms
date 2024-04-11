export enum Sexo {
    HOMBRE = 'HOMBRE',
    MUJER = 'MUJER',
    OTRO = 'OTRO'
  }

export interface Cliente {
    idUsuario: number;
    telefono: string;
    direccion: string;
    dni: string;
    fechaNacimiento: Date;
    sexo: Sexo;
    id:number;
  
  }