package es.uma.informatica.sii.spring.jpa.demo.services;


import es.uma.informatica.sii.spring.jpa.demo.entities;
import es.uma.informatica.sii.spring.jpa.demo.repositories;
import java.util.logging.Logger;


@Service
@Transactional(noRollbackFor = TokenNoValidoException.class)
public class SesionService {
    private final SesionRepository sesionRepo;

    private final JwtUtil jwtUtil;
    private final Logger log =Logger.getLogger(UsuarioService.class.getName());

    public SesionService(SesionRepository sesionRepo, JwtUtil jw){
        this.jwtUtil=jw;
        this.sesionRepo=sesionRepo;
    }

    public List<Sesion> findAll() {
        return sesionRepo.findAll();
    }


    public List<Sesion> findByIdPlan(Long idPlan) {
        return sesionRepo.findById(idPlan);
    }

    public Optional<Sesion> findByTrabajoRealizado(String trabajo) {
        return sesionRepo.findByTrabajoRealizado(trabajo);
    }

    public Optional<Sesion> findById(Long id) {
        return sesionRepo.findById(id);
    }

    public void deleteById(Long id) {
        if (!sesionRepo.existsById(id)) {
            throw new SesionInexistente(id);
        }
        usuarioRepo.deleteById(id);
    }

    public boolean existsById(Long idUsuario) {
        return sesionRepo.existsById(idUsuario);
    }

    private String generarToken() {
        return UUID.randomUUID().toString();
    }


}
