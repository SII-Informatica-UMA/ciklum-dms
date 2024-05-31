package es.uma.informatica.sii.spring.jpa.demo.dtos;

import java.lang.reflect.Array;
import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionEntrenamientoDTO extends AsignacionEntrenamientoNuevoDTO{
    private List<PlanDTO> planes;
    private Long id;
}
