package fr.l3z.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Event;
import fr.l3z.models.Family;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;

@ApplicationScoped
@Stateless
public class EventRepositoryImpl implements EventRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private SkillProfileRepository skillProfileRep;
	
	
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
		eventAModifier.setVote(t.getVote());
		eventAModifier.setAction(t.getAction());
		eventAModifier.setPurchase(t.getPurchase());

		entityManager.merge(eventAModifier);
		
	}
	
	@Override
	public List<Event> findByTask(Long taskId){
		return entityManager
			.createQuery("select u from Event u where u.task.id = :taskIdParam", Event.class)
			.setParameter("taskIdParam", taskId)
			.getResultList();
	}

	@Override
	public List<Event> findByVote(Long voteId) {
		return entityManager
				.createQuery("select u from Event u where u.vote.id = :voteIdParam", Event.class)
				.setParameter("voteIdParam", voteId)
				.getResultList();
	}

	@Override
	public List<Event> findByFamily(Long id) {
		return entityManager
				.createQuery("select u from Event u where u.family.id = :familyIdParam", Event.class)
				.setParameter("familyIdParam", id)
				.getResultList();
	}

	@Override
	public List<Event> findByUser(Long id) {
		return entityManager
				.createQuery("select u from Event u where u.user.id = :userIdParam", Event.class)
				.setParameter("userIdParam", id)
				.getResultList();
	}

	

	

}
