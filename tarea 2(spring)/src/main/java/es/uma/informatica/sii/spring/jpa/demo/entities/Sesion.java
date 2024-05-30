package es.uma.informatica.sii.spring.jpa.demo.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sesion {
	@Id
	@GeneratedValue
	private Long id;
	private Date inicio;
	private Date fin;
	private String trabajoRealizado;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "MULTIMEDIA")
	@Column(name = "valor")
	private List<String> multimedia;
	private String descripcion;
	private Boolean presencial;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "DATOSALUD")
	@Column(name = "dato")
	private List<String> datosSalud;
	private Long idPlan;
	
	/*public Sesion(Date nInicio, Date nFin, String t, List<String> m, String d, Boolean p, List<String> ds, Long idP){
		super();
		this.inicio = nInicio ;
		this.fin = nFin ;
		this.trabajoRealizado = t ;
		this.multimedia = m;
		this.descripcion = d;
		this.presencial = p;
		this.datosSalud = ds;
		this.idPlan = idP;
	}*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	public String getTrabajoRealizado() {
		return trabajoRealizado;
	}
	public void setTrabajoRealizado(String trabajoRealizado) {
		this.trabajoRealizado = trabajoRealizado;
	}
	public List<String> getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(List<String> multimedia) {
		this.multimedia = multimedia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Boolean getPresencial() {
		return presencial;
	}
	public void setPresencial(Boolean presencial) {
		this.presencial = presencial;
	}
	public List<String> getDatosSalud() {
		return datosSalud;
	}
	public void setDatosSalud(List<String> datosSalud) {
		this.datosSalud = datosSalud;
	}
	public Long getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		result = prime * result + ((fin == null) ? 0 : fin.hashCode());
		result = prime * result + ((trabajoRealizado == null) ? 0 : trabajoRealizado.hashCode());
		result = prime * result + ((multimedia == null) ? 0 : multimedia.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((presencial == null) ? 0 : presencial.hashCode());
		result = prime * result + ((datosSalud == null) ? 0 : datosSalud.hashCode());
		result = prime * result + ((idPlan == null) ? 0 : idPlan.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sesion other = (Sesion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inicio == null) {
			if (other.inicio != null)
				return false;
		} else if (!inicio.equals(other.inicio))
			return false;
		if (fin == null) {
			if (other.fin != null)
				return false;
		} else if (!fin.equals(other.fin))
			return false;
		if (trabajoRealizado == null) {
			if (other.trabajoRealizado != null)
				return false;
		} else if (!trabajoRealizado.equals(other.trabajoRealizado))
			return false;
		if (multimedia == null) {
			if (other.multimedia != null)
				return false;
		} else if (!multimedia.equals(other.multimedia))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (presencial == null) {
			if (other.presencial != null)
				return false;
		} else if (!presencial.equals(other.presencial))
			return false;
		if (datosSalud == null) {
			if (other.datosSalud != null)
				return false;
		} else if (!datosSalud.equals(other.datosSalud))
			return false;
		if (idPlan == null) {
			if (other.idPlan != null)
				return false;
		} else if (!idPlan.equals(other.idPlan))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Sesion [id=" + id + ", inicio=" + inicio + ", fin=" + fin + ", trabajoRealizado=" + trabajoRealizado
				+ ", multimedia=" + multimedia + ", descripcion=" + descripcion + ", presencial=" + presencial
				+ ", datosSalud=" + datosSalud + ", idPlan=" + idPlan + "]";
	}
	


	
	
	
	

}
