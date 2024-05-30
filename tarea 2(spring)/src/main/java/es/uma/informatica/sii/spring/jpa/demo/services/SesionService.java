package es.uma.informatica.sii.spring.jpa.demo.services;

import es.uma.informatica.sii.spring.jpa.demo.*;
import es.uma.informatica.sii.spring.jpa.demo.entities.Sesion;
import es.uma.informatica.sii.spring.jpa.demo.exceptions.SesionInexistente;
import es.uma.informatica.sii.spring.jpa.demo.exceptions.SesionRepetidaException;
import es.uma.informatica.sii.spring.jpa.demo.repositories.SesionRepository;
import es.uma.informatica.sii.spring.jpa.demo.security.JwtUtil;
import jakarta.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;



@Service
@Transactional()
public class SesionService {
    private final SesionRepository sesionRepo;

    private final JwtUtil jwtUtil;
    private final Logger log =Logger.getLogger(SesionService.class.getName());

    public SesionService(SesionRepository sesionRepo, JwtUtil jw){
        this.jwtUtil=jw;
        this.sesionRepo=sesionRepo;
    }

    public List<Sesion> findAll() {
        return sesionRepo.findAll();
    }


    public List<Sesion> findByIdPlan(Long idPlan) {
        return sesionRepo.findByIdPlan(idPlan);
    }

    public List<Sesion> findByTrabajoRealizado(String trabajo) {
        return sesionRepo.findByTrabajoRealizado(trabajo);
    }

    public Optional<Sesion> findById(Long id) {
        return sesionRepo.findById(id);
    }

    public void deleteById(Long id) {
        if (!sesionRepo.existsById(id)) {
            throw new SesionInexistente();
        }
        sesionRepo.deleteById(id);
    }

    public boolean existsById(Long idUsuario) {
        return sesionRepo.existsById(idUsuario);
    }

    private String generarToken() {
        return UUID.randomUUID().toString();
    }


    public List<Sesion> obtenerSesiones() {
		return sesionRepo.findAll();
	}

    public Sesion obtenerSesion(Long id) {
		var sesion = sesionRepo.findById(id);
		if (sesion.isPresent()) {
			return sesion.get();
		} else {
			throw new SesionInexistente();
		}	
	}

    public void aniadirSesion(Sesion sesion){
		if (sesionRepo.existsById(sesion.getId())) {
			throw new SesionRepetidaException();
		} else {
			sesionRepo.save(sesion);
		}
	}
	
    public void actualizarSesion(Sesion sesion){
        if(!sesionRepo.existsById(sesion.getId())){
            throw new SesionInexistente();
        } else{
            sesionRepo.save(sesion);
        }
    }

}
