package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.utils.builder.FiestaGoodBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.TemplateBuilder;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TemplateTest {

    @Test
    public void whenCreateATemplateForFiesta_TheyCreateCorrectly(){
        //Setup(Given)
        Good asado = FiestaGoodBuilder.buildAGood()
                                      .withName("Asado")
                                      .withQuantityForPerson(1)
                                      .withPricesPerUnit(120)
                                      .build();
        Good carbon = FiestaGoodBuilder.buildAGood()
                                       .withName("Bolsa De Carbon")
                                       .withQuantityForPerson(1)
                                       .withPricesPerUnit(60)
                                       .build();
        List<Good> goodForTemplates = Arrays.asList(asado, carbon);

        //Exercise(When)
        Template templateSUT = new Template("Asado Loco", EventType.FIESTA, goodForTemplates);

        //Test(Then)
        assertEquals("No se asigno el nombre al template al crearlo",
                     "Asado Loco",
                     templateSUT.getName());
        assertEquals("No se cargo el tipo de evento del template al crearlo",
                     EventType.FIESTA,
                     templateSUT.getEventType());
        assertEquals("No se cargaron los goods al cerar el template",
                     goodForTemplates,
                     templateSUT.getGoodsForEvent());
    }

    @Test
    public void whenAskingToATemplateIfIsForCanastaTypeAndNotItIs_ReturnFalse(){
        //Setup(Given)
        Template templateSUT = TemplateBuilder.buildATemplate()
                                              .withEventType(EventType.FIESTA)
                                              .build();
        //Exercise(When)
        Boolean isForCanasta = templateSUT.isForEvent(EventType.CANASTA);

        //Test(Then)
        assertFalse("Valido que sirve para las canastas y es para tipo fiesta!",
                    isForCanasta);
    }

}