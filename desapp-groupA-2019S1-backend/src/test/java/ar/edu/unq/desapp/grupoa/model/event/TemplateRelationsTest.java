package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaGoodBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.TemplateBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.UserBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.apache.commons.collections.ListUtils.EMPTY_LIST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TemplateRelationsTest {
    @Before
    public void setUp(){
        TemplateRelations.destroy();
    }

    @Test
    public void whenAnUserUsesATemplateTheTemplateListHaveTheTemplate() {

        //Setup(Given)
        User organizer = UserBuilder.buildAUser().build();

        LocalDateTime limitTime = LocalDateTime.now();

        Good asado = FiestaGoodBuilder.buildAGood().withName("Asado").build();

        Good laDelDiego = FiestaGoodBuilder.buildAGood().withName("La del Diego").build();

        Template fiestaLocaTemplate = TemplateBuilder.buildATemplate()
                .withEventType(EventType.FIESTA)
                .addGood(asado)
                .addGood(laDelDiego)
                .build();

        //Exercise(Exercise)
        Fiesta.createWithATemplate("La Fiesta Loca", organizer, new ArrayList<>(), limitTime, fiestaLocaTemplate);
        assertEquals("The templates list should have 1 element",
                1,
                TemplateRelations.getAllTemplates().size());

    }

    @Test
    public void whenAnUserUsesATemplateThatTemplateIsInTemplatesUsedByUser() {

        //Setup(Given)
        User organizer = UserBuilder.buildAUser().build();

        LocalDateTime limitTime = LocalDateTime.now();

        Good asado = FiestaGoodBuilder.buildAGood().withName("Asado").build();

        Good laDelDiego = FiestaGoodBuilder.buildAGood().withName("La del Diego").build();

        Template fiestaLocaTemplate = TemplateBuilder.buildATemplate()
                .withEventType(EventType.FIESTA)
                .addGood(asado)
                .addGood(laDelDiego)
                .build();

        Fiesta.createWithATemplate("La Fiesta Loca", organizer, new ArrayList<>(), limitTime, fiestaLocaTemplate);
        //Exercise(Exercise)

        assertTrue("The templates list should have 1 element",
                TemplateRelations.templatesUsedByUser(organizer).contains(fiestaLocaTemplate));

    }

    @Test
    public void whenAnUserUsesTwoTemplatesAndRequestTheRelatedTemplatesOfTheFirstOneItReturnsTheSecondOne() {

        //Setup(Given)
        User organizer = UserBuilder.buildAUser().build();

        LocalDateTime limitTime = LocalDateTime.now();

        Good asado = FiestaGoodBuilder.buildAGood().withName("Asado").build();

        Good laDelDiego = FiestaGoodBuilder.buildAGood().withName("La del Diego").build();

        Template fiestaLocaTemplate1 = TemplateBuilder.buildATemplate()
                .withEventType(EventType.FIESTA)
                .addGood(asado)
                .addGood(laDelDiego)
                .build();

        Template fiestaLocaTemplate2 = TemplateBuilder.buildATemplate()
                .withEventType(EventType.FIESTA)
                .addGood(asado)
                .build();

        Fiesta.createWithATemplate("La Fiesta Loca", organizer, new ArrayList<>(), limitTime, fiestaLocaTemplate1);
        Fiesta.createWithATemplate("La Fiesta Loca", organizer, new ArrayList<>(), limitTime, fiestaLocaTemplate2);
        //Exercise(Exercise)

        assertEquals("The element should be the template 2",
                fiestaLocaTemplate2,
                TemplateRelations.templatesRelatedTo(fiestaLocaTemplate1).get(0));

    }
}
