
export interface Sesion {
  idPlan: number;
  inicio: Date | undefined;
  fin: Date | undefined;
  trabajoRealizado: string;
  multimedia: string[];
  descripcion: string;
  presencial: boolean;
  datosSalud: string[];
  id: number;

}
