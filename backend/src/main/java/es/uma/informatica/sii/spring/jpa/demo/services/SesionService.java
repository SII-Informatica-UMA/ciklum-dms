package es.uma.informatica.sii.spring.jpa.demo.services;

import es.uma.informatica.sii.spring.jpa.demo.*;
import es.uma.informatica.sii.spring.jpa.demo.dtos.PlanDTO;
import es.uma.informatica.sii.spring.jpa.demo.entities.Sesion;
import es.uma.informatica.sii.spring.jpa.demo.exceptions.PlanInexistente;
import es.uma.informatica.sii.spring.jpa.demo.exceptions.SesionInexistente;
import es.uma.informatica.sii.spring.jpa.demo.repositories.SesionRepository;
import es.uma.informatica.sii.spring.jpa.demo.security.JwtUtil;
import jakarta.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;



@Service
@Transactional
public class SesionService {
    private final SesionRepository sesionRepo;

    private final JwtUtil jwtUtil;
    private final Logger log =Logger.getLogger(SesionService.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${entrenador.server.port}")
    int entrenaPort;

    public SesionService(SesionRepository sesionRepo, JwtUtil jw){
        this.jwtUtil=jw;
        this.sesionRepo=sesionRepo;
    }

    private URI uri(String scheme, String host, int port, String... paths) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
            .scheme(scheme)
            .host(host).port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        return ub.build();
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path) {
        String token = jwtUtil.generateToken(jwtUtil.createUserDetails("1","",List.of("ROLE_USER")));
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer "+token)
            .build();
        return peticion;
    }

    public List<Sesion> findByIdPlan(Long idPlan) {
        try{
            var peticion = get("http","localhost",entrenaPort,"/plan/" + idPlan);
            restTemplate.exchange(peticion, new ParameterizedTypeReference<PlanDTO>() {
                });
        }catch(RestClientException e){
            log.info("El plan no existe");
            throw new PlanInexistente();
        }
        return sesionRepo.findByIdPlan(idPlan);
    }


    public void deleteById(Long id) {
        if (!sesionRepo.existsById(id)) {
            throw new SesionInexistente();
        }
        sesionRepo.deleteById(id);
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
        try{
            var peticion = get("http","localhost",entrenaPort,"/plan/" + sesion.getIdPlan());
            restTemplate.exchange(peticion, new ParameterizedTypeReference<PlanDTO>() {
                });
        }catch(RestClientException e){
            log.info("El plan no existe");
            throw new PlanInexistente();
        }
		sesionRepo.save(sesion);
	}

    public void actualizarSesion(Sesion sesion){
        try{
            var peticion = get("http","localhost",entrenaPort,"/plan/" + sesion.getIdPlan());
            restTemplate.exchange(peticion, new ParameterizedTypeReference<PlanDTO>() {
                });
        }catch(RestClientException e){
            log.info("El plan no existe");
            throw new PlanInexistente();
        }
        if(!sesionRepo.existsById(sesion.getId())){
            throw new SesionInexistente();
        } else{
            sesionRepo.save(sesion);
        }
    }

}
