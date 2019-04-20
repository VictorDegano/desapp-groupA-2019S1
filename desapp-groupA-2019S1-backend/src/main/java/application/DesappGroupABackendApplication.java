package application;

import application.model.event.Fiesta;
import application.persistence.FiestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DesappGroupABackendApplication {

	@Autowired
	private FiestaRepository fiestaRepo;

	public static void main(String[] args) {
		SpringApplication.run(DesappGroupABackendApplication.class, args);
	}

	@Bean
	public void load(){
		// TODO: 18/4/2019 reemplazar la creacion de las fiestas por un builder
		Fiesta fiesta1 = new Fiesta("Pepe Loco", List.of("Ivan", "Ivan"), LocalDateTime.now(),List.of());
        Fiesta fiesta2 = new Fiesta("Ivan", List.of("Ivan", "Pepe Loco", "Victor"), LocalDateTime.now(),List.of());

        fiestaRepo.createOfAList(List.of(fiesta1, fiesta2));

	}

}
