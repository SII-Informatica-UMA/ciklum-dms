package es.uma.informatica.sii.spring.jpa.demo.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.uma.informatica.sii.spring.jpa.demo.entities.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
	List<Sesion> findByIdPlan(Long idPlan);
	List<Sesion> findByTrabajoRealizado(String trabajoRealizado);
	
	@Query("select s from Sesion s where (s.inicio.compareTo(:fecha))>=0")
	List<Sesion> miConsultaCompleja(@Param("fecha") Date fecha);
}
