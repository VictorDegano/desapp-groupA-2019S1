package application.persistence;

import application.model.event.Fiesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FiestaRepository {

    private FiestaDAO fiestaDao;

    @Autowired
    public FiestaRepository(FiestaDAO aFiestaDAO){
        this.fiestaDao = aFiestaDAO;
    }

    public FiestaRepository() { }

    public Integer create (Fiesta aFiestaToCreate){
        this.fiestaDao.save(aFiestaToCreate);

        return aFiestaToCreate.getId();
    }

    public List<Integer> createOfAList(List<Fiesta> aListOfFiestas){
        this.fiestaDao.saveAll(aListOfFiestas);
        List<Integer> idList = aListOfFiestas.stream()
                                            .map(Fiesta::getId)
                                            .collect(Collectors.toList());
        return idList;
    }
}
