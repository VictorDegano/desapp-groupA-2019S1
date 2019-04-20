package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import ar.edu.unq.desapp.grupoa.persistence.FiestaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FiestaService {

    private FiestaDAO fiestaDao;

    @Autowired
    public FiestaService(FiestaDAO aFiestaDAO){
        this.fiestaDao = aFiestaDAO;
    }

    public FiestaService() { }

    public Integer create (Fiesta aFiestaToCreate){
        this.fiestaDao.save(aFiestaToCreate);
        return aFiestaToCreate.getId();
    }

    public List<Integer> createAll(List<Fiesta> aListOfFiestas){
        this.fiestaDao.saveAll(aListOfFiestas);
        List<Integer> idList = aListOfFiestas.stream()
                                            .map(Fiesta::getId)
                                            .collect(Collectors.toList());
        return idList;
    }
}
