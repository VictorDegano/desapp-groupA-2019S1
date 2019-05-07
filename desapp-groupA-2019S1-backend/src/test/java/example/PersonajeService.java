package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonajeService {

    @Autowired
    private PersonajeDAO personajeDAO;

    public Integer create(Personaje personaje) {
        personajeDAO.save(personaje);
        return personaje.getId();
    }

    public Personaje getById(Integer id) {
        return personajeDAO.findById(id).get();
    }

    public void update(Personaje personaje) {
        personajeDAO.save(personaje);
    }

    @Transactional
    public void pickUpItem(Integer personajeId, Item item) {
        Personaje personaje = personajeDAO.getOne(personajeId);
        personaje.pickUpItem(item);
        update(personaje);
    }
}