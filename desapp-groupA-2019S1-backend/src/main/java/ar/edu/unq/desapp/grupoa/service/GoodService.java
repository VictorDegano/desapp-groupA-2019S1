package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.persistence.GoodDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class GoodService {

    @Autowired
    private GoodDAO goodDAO;


    public Good findById(Integer id) {
        return goodDAO.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void update(Good good) {
        goodDAO.save(good);
    }



    public void delete(Integer goodId) {
        Good good = findById(goodId);
        goodDAO.delete(good);
    }
}
