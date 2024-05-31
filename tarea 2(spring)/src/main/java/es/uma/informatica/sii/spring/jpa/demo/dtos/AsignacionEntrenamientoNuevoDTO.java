package es.uma.informatica.sii.spring.jpa.demo.dtos;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "builderNuevo")
public class AsignacionEntrenamientoNuevoDTO {
    private Long idEntrenador;
    private Long idCliente;
    private String especialidad;

}
