package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.SkillNote;

@ApplicationScoped
@Stateless
public class SkillNoteRepositoryImpl implements SkillNoteRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public SkillNoteRepositoryImpl() {
		
	}
	
	@Override
	public SkillNote save(SkillNote t) {
		System.out.println("skillNoteRep : skillNote = "+t);
		entityManager.persist(t);
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
		entityManager.remove(entityManager.find(SkillNote.class, id));
		
	}

	@Override
	public void deleteByObject(SkillNote t) {
		entityManager.remove(entityManager.find(SkillNote.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, SkillNote t) {
		SkillNote skillNoteAModifier=entityManager.find(SkillNote.class,idAModifier);
		skillNoteAModifier.setSkill(t.getSkill());
		skillNoteAModifier.setScore(t.getScore());
		entityManager.merge(skillNoteAModifier);
		
	}



}
