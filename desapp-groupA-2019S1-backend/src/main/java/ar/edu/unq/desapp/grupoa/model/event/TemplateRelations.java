package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateRelations {
    private static List<Template> templates = new ArrayList<>();

    public static List<Template> getAllTemplates() {
        if(templates == null){
            templates = new ArrayList<>();
            return templates;
        }else{
            return templates;
        }
    }

    public static void addTemplate(Template template) {
        getAllTemplates().add(template);
    }

    public static List<Template> templatesUsedByUser(User user) {
        return getAllTemplates().stream().filter(t->t.getUsersThatUsedIt().contains(user)).collect(Collectors.toList());
    }

    public static void useTemplate(User user, Template template) {
        template.addUser(user);
        TemplateRelations.addTemplate(template);
    }

    public static List<Template> templatesRelatedTo(Template template) {
        List<User> usersThatUseThisTemplate = template.getUsersThatUsedIt();
        List<Template> templatesAux = new ArrayList<>();
        for(Template t  : getAllTemplates()){
            if(t.getUsersThatUsedIt().stream().anyMatch(u->usersThatUseThisTemplate.contains(u)) && !template.equals(t)){
                templatesAux.add(t);
            }
        }
        return templatesAux;
    }

    public static void destroy() {
        templates = null;
    }
}
