package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Vote;

@ApplicationScoped
@Stateless
public class VoteRepositoryImpl implements VoteRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public VoteRepositoryImpl() {
		
	}
	
	@Override
	public Vote save(Vote t) {
		entityManager.persist(t);
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
		entityManager.remove(entityManager.find(Vote.class, id));
		
	}

	@Override
	public void deleteByObject(Vote t) {
		entityManager.remove(entityManager.find(Vote.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, Vote t) {
		Vote voteAModifier=entityManager.find(Vote.class,idAModifier);
		voteAModifier.setSkillProfile(t.getSkillProfile());
		voteAModifier.setOpenToVote(t.getOpenToVote());
		voteAModifier.setOriginalTask(t.getOriginalTask());
		voteAModifier.setTaskNewName(t.getTaskNewName());
		voteAModifier.setTaskNewDescription(t.getTaskNewDescription());
		voteAModifier.setTaskNewRepeatAfter(t.getTaskNewRepeatAfter());
		voteAModifier.setTaskNewSPMDo(t.getTaskNewSPMDo());
		entityManager.merge(voteAModifier);
		
	}

	

}
