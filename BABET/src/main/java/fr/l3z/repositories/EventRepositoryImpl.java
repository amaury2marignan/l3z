package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Event;

@ApplicationScoped
@Stateless
public class EventRepositoryImpl implements EventRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public EventRepositoryImpl() {
		
	}
	
	@Override
	public Event save(Event t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Event find(Long id) {
		return entityManager
				.createQuery("select u from Event u where u.id = :eventIdParam", Event.class)
				.setParameter("eventIdParam", id)
				.getSingleResult();
	}

	@Override
	public Event findByObject(Event t) {
		Event event = entityManager.find(Event.class, t.getId());
		return event;
	}

	@Override
	public List<Event> findAll() {
		return entityManager.createQuery("select u from Event u", Event.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(entityManager.find(Event.class, id));
		
	}

	@Override
	public void deleteByObject(Event t) {
		entityManager.remove(entityManager.find(Event.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, Event t) {
		Event eventAModifier=entityManager.find(Event.class,idAModifier);
		eventAModifier.setUser(t.getUser());
		eventAModifier.setDate(t.getDate());
		eventAModifier.setTask(t.getTask());
		eventAModifier.setProject(t.getProject());
		eventAModifier.setRule(t.getRule());
		eventAModifier.setDomain(t.getDomain());
		eventAModifier.setAction(t.getAction());

		entityManager.merge(eventAModifier);
		
	}

	

}
