package es.uma.informatica.sii.spring.jpa.demo.dtos;

import java.sql.Date;
import java.util.List;

import es.uma.informatica.sii.spring.jpa.demo.entities.Sesion;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "builderNuevo")
public class SesionNuevaDTO {
	private Date inicio;
	private Date fin;
	private String trabajoRealizado;
	private List<String> multimedia;
	private String descripcion;
	private Boolean presencial;
	private List<String> datosSalud;
	private Long idPlan;

	public Sesion toEntity() {
		return Sesion.builder()
				.inicio(this.inicio)
				.fin(this.fin)
				.trabajoRealizado(this.trabajoRealizado)
				.multimedia(this.multimedia)
				.descripcion(this.descripcion)
				.presencial(this.presencial)
				.datosSalud(this.datosSalud)
				.idPlan(this.idPlan)
				.build();
	}
}
