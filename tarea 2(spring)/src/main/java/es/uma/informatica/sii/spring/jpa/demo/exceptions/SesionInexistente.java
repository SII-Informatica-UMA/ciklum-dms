package es.uma.informatica.sii.spring.jpa.demo.exceptions;

import es.uma.informatica.sii.spring.jpa.demo.entities.Sesion;

public class SesionInexistente extends RuntimeException{

    public SesionInexistente() {
        super();
    };
}
