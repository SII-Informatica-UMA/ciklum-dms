package es.uma.informatica.sii.spring.jpa.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@CrossOrigin()
@Tag(name = "Gestión de sesiones", description="Operaciones para la gestion de sesiones")
public class GestionSesion {
    private final SesionService sesionService;

    public GestionSesion(SesionService sesionService){
        this.sesionService = sesionService;
    }

    @GetMapping()
    public ResponseEntity<List<SesionDTO>> obtenerSesiones(@RequestParam(value = "plan") int plan){

    }

    @PostMapping()
    public ResponseEntity<SesionDTO> crearSesion (@RequestBody SesionNuevaDTO sesionDTO){

    }

    @GetMapping("/{idSesion}")
    public ResponseEntity<SesionDTO> getSesion (@PathVariable Long idSesion){

    }

    @PutMapping("/{idSesion}")
    public ResponseEntity<SesionDTO> actualizaSesion (@PathVariable Long idSesion,@RequestBody SesionDTO sesionDTO){
        
    }

    @DeleteMapping("/{idSesion}")
    public ResponseEntity<SesionDTO> borraSesion (@PathVariable Long idSesion){
        
    }
}
