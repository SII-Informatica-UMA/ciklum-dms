package es.uma.informatica.sii.spring.jpa.demo.controllers;

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
    public List<Sesion>
}
