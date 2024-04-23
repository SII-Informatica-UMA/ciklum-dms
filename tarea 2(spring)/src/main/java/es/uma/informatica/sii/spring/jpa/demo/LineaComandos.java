package es.uma.informatica.sii.spring.jpa.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.uma.informatica.sii.spring.jpa.demo.entities.Sesion;
import es.uma.informatica.sii.spring.jpa.demo.repositories.SesionRepository;

@Component
public class LineaComandos implements CommandLineRunner {
	private SesionRepository repository;
	public LineaComandos(SesionRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		for (String s: args) {
			System.out.println(s);
		}

		if (args.length > 0) {
			for (Sesion s: repository.findByTrabajoRealizado(args[0])) {
				System.out.println(s);
			}
		}
	}

}
