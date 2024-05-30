package es.uma.informatica.sii.spring.jpa.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import es.uma.informatica.sii.spring.jpa.demo.services.SesionService;
import es.uma.informatica.sii.spring.jpa.demo.dtos.SesionDTO;
import es.uma.informatica.sii.spring.jpa.demo.dtos.SesionNuevaDTO;
import es.uma.informatica.sii.spring.jpa.demo.entities.Sesion;
import es.uma.informatica.sii.spring.jpa.demo.exceptions.SesionInexistente;
import es.uma.informatica.sii.spring.jpa.demo.exceptions.SesionRepetidaException;

import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/sesion")
@CrossOrigin()
public class GestionSesion {
    private final SesionService sesionService;

    public GestionSesion(SesionService sesionService){
        this.sesionService = sesionService;
    }

    @GetMapping()
    public ResponseEntity<List<SesionDTO>> obtenerSesiones(@RequestParam(value = "plan") Long plan){
        try{
            
            List<SesionDTO> lista = new ArrayList<>();
            for(Sesion s : sesionService.findByIdPlan(plan)){
                lista.add(SesionDTO.fromEntity(s));
            }
            return ResponseEntity.ok(lista);
        }catch(SesionInexistente e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<SesionDTO> crearSesion (@RequestBody SesionNuevaDTO sesionDTO,@RequestParam(value = "plan") Long plan,UriComponentsBuilder uriBuilder){
        try{
            Sesion sesion = sesionDTO.toEntity();
            sesion.setIdPlan(plan);
            sesionService.aniadirSesion(sesion);
            return ResponseEntity.created(uriBuilder.path("/sesion/" + sesion.getId()).build().toUri()).build();
        } catch(SesionRepetidaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idSesion}")
    public ResponseEntity<SesionDTO> getSesion (@PathVariable Long idSesion){
        try{
            Sesion sesion = sesionService.obtenerSesion(idSesion);
            return ResponseEntity.ok(SesionDTO.fromEntity(sesion));
        }catch(SesionInexistente e){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{idSesion}")
    public ResponseEntity<SesionDTO> actualizaSesion (@PathVariable Long idSesion,@RequestBody SesionDTO sesionDTO){
        try{
            Sesion sesion = sesionDTO.toEntity();
            sesion.setId(idSesion);
            sesionService.actualizarSesion(sesion);
            return ResponseEntity.ok(SesionDTO.fromEntity(sesion));
        }catch(SesionInexistente e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idSesion}")
    public ResponseEntity<?> borraSesion (@PathVariable Long idSesion){
        try {
            sesionService.deleteById(idSesion);
            return ResponseEntity.ok().build();
        } catch (SesionInexistente e) {
            return ResponseEntity.notFound().build();
        }
    }
}
