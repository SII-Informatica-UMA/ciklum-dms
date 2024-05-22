package es.uma.informatica.sii.spring.jpa.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uma.informatica.sii.spring.jpa.demo.repositories.SesionRepository;

@Service
@Transactional
public class SesionService {
    private final SesionRepository sesionRepo;
    
}
