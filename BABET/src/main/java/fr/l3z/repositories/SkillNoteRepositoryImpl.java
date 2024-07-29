package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import fr.l3z.models.SkillNote;

@ApplicationScoped
@Stateless
public class SkillNoteRepositoryImpl implements SkillNoteRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public SkillNoteRepositoryImpl() {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("babetdb");
	        this.entityManager = emf.createEntityManager();
	}
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		
	}
	
	@Override
	public SkillNote save(SkillNote t) {
		entityManager.getTransaction().begin();
		System.out.println("skillNoteRep : skillNote = "+t);
		entityManager.persist(t);
		 entityManager.getTransaction().commit();
		return t;
	}

	@Override
	public SkillNote find(Long id) {
		return entityManager
				.createQuery("select u from SkillNote u where u.id = :skillNoteIdParam", SkillNote.class)
				.setParameter("skillNoteIdParam", id)
				.getSingleResult();
	}

	@Override
	public SkillNote findByObject(SkillNote t) {
		SkillNote skillNote = entityManager.find(SkillNote.class, t.getId());
		return skillNote;
	}

	@Override
	public List<SkillNote> findAll() {
		return entityManager.createQuery("select u from SkillNote u", SkillNote.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(SkillNote.class, id));
		entityManager.getTransaction().commit();
	}

	@Override
	public void deleteByObject(SkillNote t) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(SkillNote.class, t.getId()));
		entityManager.getTransaction().commit();
	}

	@Override
	public void update(Long idAModifier, SkillNote t) {
		entityManager.getTransaction().begin();
		SkillNote skillNoteAModifier=entityManager.find(SkillNote.class,idAModifier);
		skillNoteAModifier.setSkill(t.getSkill());
		skillNoteAModifier.setScore(t.getScore());
		entityManager.merge(skillNoteAModifier);
		entityManager.getTransaction().commit();
	}



}
