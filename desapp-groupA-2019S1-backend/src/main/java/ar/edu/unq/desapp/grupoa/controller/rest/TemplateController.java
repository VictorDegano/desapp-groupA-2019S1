package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.TemplateDTO;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Transactional
@Controller
@RequestMapping(value = "/templates", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class TemplateController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TemplateService templateService;

    public TemplateController() {
        LOGGER.info("Starting Event Controller");
    }

    @GetMapping("/{eventType}")
    public ResponseEntity<List<TemplateDTO>> getTemplates(@PathVariable String eventType){
        LOGGER.info("Got request to GET templates");

        List<Template> templates = templateService.getTemplates(eventType);
        List<TemplateDTO> templatesDTO = templates.stream().map(TemplateDTO::from).collect(Collectors.toList());

        LOGGER.info("Responding with Event Lists {}", templatesDTO);
        return new ResponseEntity<>(templatesDTO, HttpStatus.OK);
    }

    @PutMapping("/useTemplate/{templateId}/{userID}")
    public ResponseEntity<String> useTemplate(@PathVariable Integer templateId, @PathVariable Integer userId){
        LOGGER.info("Got request for user {}  to Use template {} ",userId,templateId);

        templateService.useTemplate(templateId,userId);

        LOGGER.info("User {} was succesfully registered using the template {}", userId, templateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
