package ar.edu.unq.desapp.grupoa.exception;

public class TemplateNotFoundException extends RuntimeException {
    public TemplateNotFoundException(Integer templateId) {
        super ("template with id: " + templateId + " not found");
    }
}
