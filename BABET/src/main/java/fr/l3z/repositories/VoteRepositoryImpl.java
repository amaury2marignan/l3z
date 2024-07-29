package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Vote;

@ApplicationScoped
@Stateless
public class VoteRepositoryImpl implements VoteRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public VoteRepositoryImpl() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("babetdb");
        this.entityManager = emf.createEntityManager();
	}
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		
	}
	
	@Override
	public Vote save(Vote t) {
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		 entityManager.getTransaction().commit();
		return t;
	}

	@Override
	public Vote find(Long id) {
		return entityManager
				.createQuery("select u from Vote u where u.id = :voteIdParam", Vote.class)
				.setParameter("voteIdParam", id)
				.getSingleResult();
	}

	@Override
	public Vote findByObject(Vote t) {
		Vote vote = entityManager.find(Vote.class, t.getId());
		return vote;
	}

	@Override
	public List<Vote> findAll() {
		return entityManager.createQuery("select u from Vote u", Vote.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Vote.class, id));
		 entityManager.getTransaction().commit();
		
	}

	@Override
	public void deleteByObject(Vote t) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Vote.class, t.getId()));
		entityManager.getTransaction().commit();
		
	}

	@Override
	public void update(Long idAModifier, Vote t) {
		entityManager.getTransaction().begin();
		Vote voteAModifier=entityManager.find(Vote.class,idAModifier);
		voteAModifier.setSkillProfile(t.getSkillProfile());
		voteAModifier.setOpenToVote(t.getOpenToVote());
		voteAModifier.setOriginalTask(t.getOriginalTask());
		voteAModifier.setTaskNewName(t.getTaskNewName());
		voteAModifier.setTaskNewDescription(t.getTaskNewDescription());
		voteAModifier.setTaskNewRepeatAfter(t.getTaskNewRepeatAfter());
		voteAModifier.setTaskNewSPMDo(t.getTaskNewSPMDo());
		entityManager.merge(voteAModifier);
		entityManager.getTransaction().commit();
	}

	

}
