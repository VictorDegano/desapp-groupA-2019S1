package ar.edu.unq.desapp.grupoa;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.service.FiestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.apache.commons.collections.ListUtils.EMPTY_LIST;

@SpringBootApplication
public class DesappGroupABackendApplication {

	@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
	@Autowired
	private FiestaService fiestaRepo;

	public static void main(String[] args) {
		SpringApplication.run(DesappGroupABackendApplication.class, args);
	}

	@SuppressWarnings("unchecked")
	@Bean
	public void load(){
		// TODO: 18/4/2019 reemplazar la creacion de las fiestas por un builder
		Fiesta fiesta1 = new Fiesta("Pepe Loco", Arrays.asList("Ivan","Ivan"), LocalDateTime.now(), EMPTY_LIST);
		Fiesta fiesta2 = new Fiesta("Ivan", Arrays.asList("Victor","Pepe el loco", "Ivan"), LocalDateTime.now(),EMPTY_LIST);

        fiestaRepo.createOfAList(Arrays.asList(fiesta1,fiesta2));

	}

}
