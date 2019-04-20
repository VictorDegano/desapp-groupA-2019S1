package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.apache.commons.collections.ListUtils.EMPTY_LIST;

@Service
public class BootstrapService {

    @Autowired
    private FiestaService fiestaService;

    BootstrapService(){
    }

    public void createExampleData() {
        this.fiestaService.createAll(Arrays.asList(pepeLocoFiesta(),ivanFiesta()));
    }

    private Fiesta ivanFiesta() {
        return new Fiesta("Ivan", Arrays.asList("Victor","Pepe el loco", "Ivan"), LocalDateTime.now(),EMPTY_LIST);
    }

    private Fiesta pepeLocoFiesta() {
        return new Fiesta("Pepe Loco", Arrays.asList("Ivan","Ivan"), LocalDateTime.now(), EMPTY_LIST);
    }
}
