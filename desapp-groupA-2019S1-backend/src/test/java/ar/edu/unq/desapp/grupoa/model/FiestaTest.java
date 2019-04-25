package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.testUtils.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.testUtils.GuestBuilder;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FiestaTest {

    @Test
    public void whenAGuestConfirmTheAssistance_TheQuantityOfConfirmationsChange(){
        //Setup(Given)
        Guest firstGuest = GuestBuilder.buildAGuest()
                                        .withName("Ivan")
                                        .build();

        Guest secondGuest = GuestBuilder.buildAGuest()
                                        .withName("Victor")
                                        .build();

        Fiesta fiestaSUT = FiestaBuilder.buildAGuest()
                                        .addGuest(firstGuest)
                                        .addGuest(secondGuest)
                                        .build();

        //Exercise(When)
        fiestaSUT.confirmAsistanceOf("Ivan");
        
        //Test(Then

    }


//    @Test
//    public void whenAGuestConfirmTheAsistance_TheQuantityOfGoodsAreRecalculated(){
//        //Setup(Given)
//        Guest firestGuest = new Guest("Ivan");
//        Guest secondGuest = new Guest("Victor");
//        List<Guest> aGuestList = Arrays.asList(firestGuest, secondGuest);
//        List<Good> aGoodList = Arrays.asList(new Good("Cerveza", 50, 1), new Good("Vacio", 150, 1));
//        Fiesta fiestaSUT = new Fiesta();
//        fiestaSUT.setOrganizer("Pepe Fiestero");
//        fiestaSUT.setGuest(aGuestList);
//        fiestaSUT.setGoodsForGuest(aGoodList);
//
//        //Exercise(When)
//        fiestaSUT.confirmGuestAssistance("Ivan");
//
//        //Test(Then)
//        assertEquals(200, fiestaSUT.mercaderiaAComprar());
//    }

}