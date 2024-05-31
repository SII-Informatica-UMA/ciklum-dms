package es.uma.informatica.sii.spring.jpa.demo.dtos;
import java.sql.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "builderNuevo")
public class PlanNuevoDTO {
    private Date fechaInicio;
    private Date fechaFin;
    private String reglaRecurrencia;
    private Long idRutina;
}
