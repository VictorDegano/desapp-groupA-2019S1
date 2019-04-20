package ar.edu.unq.desapp.grupoa.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonajeService {

    private PersonajeDAO personajeDao;

    @Autowired
    public PersonajeService(PersonajeDAO personajeDAO) {
        this.personajeDao=personajeDAO;
    }
    public Integer create(Personaje personaje) {
        personajeDao.save(personaje);
        return personaje.getId();
    }

    public Personaje getById(Integer id) {
        return personajeDao.findById(id).get();
    }

    public void update(Personaje personaje) {
        personajeDao.save(personaje);
    }

    public void pickUpItem(Integer personajeId, Item item) {
        Personaje personaje = personajeDao.getOne(personajeId);
        personaje.pickUpItem(item);
        update(personaje);
    }
}