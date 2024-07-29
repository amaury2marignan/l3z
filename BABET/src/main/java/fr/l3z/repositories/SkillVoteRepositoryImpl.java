package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import fr.l3z.models.SkillVote;

@ApplicationScoped
@Stateless
public class SkillVoteRepositoryImpl implements SkillVoteRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public SkillVoteRepositoryImpl() {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("babetdb");
	        this.entityManager = emf.createEntityManager();
		
	}
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		
	}
	
	@Override
	public SkillVote save(SkillVote t) {
		 entityManager.getTransaction().begin();
	        entityManager.persist(t);
	        entityManager.getTransaction().commit();
	        return t;
	}

	@Override
	public SkillVote find(Long id) {
		return entityManager
				.createQuery("select u from SkillVote u where u.id = :skillVoteIdParam", SkillVote.class)
				.setParameter("skillVoteIdParam", id)
				.getSingleResult();
	}

	@Override
	public SkillVote findByObject(SkillVote t) {
		SkillVote skillVote = entityManager.find(SkillVote.class, t.getId());
		return skillVote;
	}

	@Override
	public List<SkillVote> findAll() {
		return entityManager.createQuery("select u from SkillVote u", SkillVote.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
	     entityManager.getTransaction().begin();
	        entityManager.remove(entityManager.find(SkillVote.class, id));
	        entityManager.getTransaction().commit();
		
	}

	@Override
	public void deleteByObject(SkillVote t) {
		 entityManager.getTransaction().begin();
	        entityManager.remove(entityManager.find(SkillVote.class, t.getId()));
	        entityManager.getTransaction().commit();
		
	}

	@Override
	public void update(Long idAModifier, SkillVote t) {
		   entityManager.getTransaction().begin();
	        SkillVote skillVoteAModifier = entityManager.find(SkillVote.class, idAModifier);
	        skillVoteAModifier.setSkill(t.getSkill());
	        skillVoteAModifier.setWhoAsked(t.getWhoAsked());
	        skillVoteAModifier.setWhoValidate(t.getWhoValidate());
	        skillVoteAModifier.setStatus(t.getStatus());
	        entityManager.merge(skillVoteAModifier);
	        entityManager.getTransaction().commit();
		
	}

	@Override
	public List<SkillVote> findAllToDecide() {
		return entityManager
				.createQuery("select u from SkillVote u where u.status= :skillVoteStatusParam", SkillVote.class)
				.setParameter("skillVoteStatusParam", 1)
				.getResultList();
	}

	

}
