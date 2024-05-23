
public class SesionInexistente {
    public SesionInexistente(Sesion sesion) {
        this(sesion.getId());
    }

    public SesionInexistente(Long id) {
        super("La sesion con ID " +id + " no existe");
    }

    public SesionInexistente() {
        super();
    };
}
