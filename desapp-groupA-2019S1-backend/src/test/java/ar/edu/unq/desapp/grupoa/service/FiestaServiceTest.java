package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.persistence.FiestaDAO;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;

// TODO: 7/5/2019 si esta es la implementacion que va a mantenerse hay que testear todos los casos
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class FiestaServiceTest {

    @Autowired
    private FiestaService fiestaService;

    @Autowired
    private FiestaDAO fiestaDAO;

    @Test
    public void whenCreateAFiesta_ItsSavedCorrectly() {
        //Setup(Given)
        LocalDateTime timeOfFiesta = LocalDateTime.of(2018,12,20,22,55,10);

        Fiesta fiesta = FiestaBuilder.buildAFiesta()
                                     .withLimitConfirmationDateTime(timeOfFiesta)
                                     .withName("La Fiesta loca")
                                     .withConfirmations(0)
                                     .build();

        //Exercise(When)
        Integer id = this.fiestaService.create(fiesta);

        //Test(Then)
        Fiesta fiestaReturned = this.fiestaService.getById(id);

        assertEquals(fiesta.getId(), fiestaReturned.getId());
        assertEquals(timeOfFiesta, fiestaReturned.getLimitConfirmationDateTime());
        assertEquals("La Fiesta loca", fiestaReturned.getName());
        assertEquals(Integer.valueOf(0), fiestaReturned.getConfirmations());
    }

    @Test
    public void whenCreateAListOfFiestas_AllAreSavedCorrectly() {
        //Setup(Given)
        LocalDateTime timeOfFiesta = LocalDateTime.of(2012,11,23,12,23,33);

        Fiesta fiesta1 = FiestaBuilder.buildAFiesta()
                                      .withLimitConfirmationDateTime(timeOfFiesta)
                                      .withName("La Fiesta loca")
                                      .withConfirmations(0)
                                      .build();

        Fiesta fiesta2 = FiestaBuilder.buildAFiesta()
                                      .withLimitConfirmationDateTime(timeOfFiesta)
                                      .withName("Pepellooza")
                                      .withConfirmations(1590)
                                      .build();

        //Exercise(When)
        this.fiestaService.createAll(Arrays.asList(fiesta1,fiesta2));

        //Test(Then)
        Fiesta fiesta1Returned = this.fiestaService.getById(fiesta1.getId());
        Fiesta fiesta2Returned = this.fiestaService.getById(fiesta2.getId());

        assertEquals(fiesta1.getId(), fiesta1Returned.getId());
        assertEquals(timeOfFiesta, fiesta1Returned.getLimitConfirmationDateTime());
        assertEquals("La Fiesta loca", fiesta1Returned.getName());
        assertEquals(Integer.valueOf(0), fiesta1Returned.getConfirmations());

        assertEquals(fiesta2.getId(), fiesta2Returned.getId());
        assertEquals(timeOfFiesta, fiesta2Returned.getLimitConfirmationDateTime());
        assertEquals("Pepellooza", fiesta2Returned.getName());
        assertEquals(Integer.valueOf(1590), fiesta2Returned.getConfirmations());
    }

}