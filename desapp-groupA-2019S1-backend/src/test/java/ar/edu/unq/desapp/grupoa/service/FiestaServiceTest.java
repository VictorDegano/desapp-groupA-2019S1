package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.persistence.FiestaDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.Assert.*;

public class FiestaServiceTest {

    private FiestaService fiestaServiceSUT;

    @Autowired
    private FiestaDAO fiestaDao;

    @Test
    public void whenTryToCreateAFiesta_IsSavedCorrectlyInTheDB() {
        //Setup(Given)
        this.fiestaServiceSUT = new FiestaService(this.fiestaDao);
        LocalDateTime creationDate = LocalDateTime.now();
        Fiesta aFiesta = new Fiesta("Jose", Arrays.asList("Pepe el loco", "Pepon"), creationDate, EMPTY_LIST);

        //Exercise(When)
        this.fiestaServiceSUT.create(aFiesta);

        //Test(Then)
    }

    @Test
    public void createAll() {
    }

    @Test
    public void getById() {
    }
}