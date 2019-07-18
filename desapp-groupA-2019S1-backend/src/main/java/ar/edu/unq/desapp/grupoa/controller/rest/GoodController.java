package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.GoodDTO;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.FiestaGood;

import ar.edu.unq.desapp.grupoa.service.EventService;
import ar.edu.unq.desapp.grupoa.service.GoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@CrossOrigin
@Transactional
@Controller
@RequestMapping(value = "/good", method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT})
public class GoodController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodService goodService;

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/{goodId}")
    public ResponseEntity<GoodDTO> getGood(@PathVariable Integer goodId) {
        Good good = goodService.findById(goodId);

        GoodDTO goodDTO = getGoodDTO(good);

        return new ResponseEntity<GoodDTO>(goodDTO, HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<String> putGood(@RequestBody GoodDTO goodDTO) {

        Good good = goodService.findById(goodDTO.getId());

        Optional.ofNullable(goodDTO.getName()).ifPresent(good::setName);
        Optional.ofNullable(goodDTO.getPricePerUnit()).ifPresent(good::setPricePerUnit);
        Optional.ofNullable(goodDTO.getQuantityForPerson()).ifPresent(good::setQuantityForPerson);

        if (good.getClass().equals(FiestaGood.class)) {
            Optional.ofNullable(goodDTO.getFinalQuantity()).ifPresent(((FiestaGood) good)::setFinalQuantity);
        }

        goodService.update(good);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{eventId}/{goodId}")
    public ResponseEntity<String> deleteGood(@PathVariable Integer eventId,@PathVariable Integer goodId) {
        //Estoy hay que hacerlo con El EventService
        eventService.deleteGoodFromEvent(eventId,goodId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private GoodDTO getGoodDTO(Good good) {
        GoodDTO goodDTO;
        if (good.getClass().equals(FiestaGood.class)) {
            goodDTO = GoodDTO.fromFiestaGood(good);
        } else {
            if (good.getClass().equals(CanastaGood.class)) {
                goodDTO = GoodDTO.fromCanastaGood(good);
            } else {
                goodDTO = GoodDTO.fromGood(good);
            }
        }
        return goodDTO;
    }

}
