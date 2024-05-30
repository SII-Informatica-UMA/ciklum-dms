package es.uma.informatica.sii.spring.jpa.demo.dtos;

import java.sql.Date;
import java.util.List;

import es.uma.informatica.sii.spring.jpa.demo.entities.Sesion;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SesionDTO extends SesionNuevaDTO {
	private Long id;

	
	@Builder
	public SesionDTO(Long id, Long idPlan, Date inicio, Date fin, String trabajoRealizado, List<String> multimedia, String descripcion, Boolean presencial, List<String> datosSalud) {
		super(inicio, fin, trabajoRealizado, multimedia, descripcion, presencial, datosSalud,idPlan);
		this.id = id;
	}

	public static SesionDTO fromEntity(Sesion sesion) {
		return SesionDTO.builder()
				.id(sesion.getId())
				.inicio(sesion.getInicio())
				.fin(sesion.getFin())
				.trabajoRealizado(sesion.getTrabajoRealizado())
				.multimedia(sesion.getMultimedia())
				.descripcion(sesion.getDescripcion())
				.presencial(sesion.getPresencial())
				.datosSalud(sesion.getDatosSalud())
				.idPlan(sesion.getIdPlan())
				.build();
	}

	/* public Sesion toEntity() {
		return Sesion.builder()
				.id(getId())
				.inicio(getInicio())
				.fin(getFin())
				.trabajoRealizado(getTrabajoRealizado())
				.multimedia(getMultimedia())
				.descripcion(getDescripcion())
				.presencial(getPresencial())
				.datosSalud(getDatosSalud())
				.build();
	}
	*/
}
 
