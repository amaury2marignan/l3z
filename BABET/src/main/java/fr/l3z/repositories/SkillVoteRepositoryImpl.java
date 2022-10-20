package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.SkillVote;

@ApplicationScoped
@Stateless
public class SkillVoteRepositoryImpl implements SkillVoteRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public SkillVoteRepositoryImpl() {
		
	}
	
	@Override
	public SkillVote save(SkillVote t) {
		entityManager.persist(t);
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
		entityManager.remove(entityManager.find(SkillVote.class, id));
		
	}

	@Override
	public void deleteByObject(SkillVote t) {
		entityManager.remove(entityManager.find(SkillVote.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, SkillVote t) {
		SkillVote skillVoteAModifier=entityManager.find(SkillVote.class,idAModifier);
		skillVoteAModifier.setSkill(t.getSkill());
		skillVoteAModifier.setWhoAsked(t.getWhoAsked());
		skillVoteAModifier.setWhoValidate(t.getWhoValidate());
		skillVoteAModifier.setStatus(t.getStatus());
		entityManager.merge(skillVoteAModifier);
		
	}

	@Override
	public List<SkillVote> findAllToDecide() {
		return entityManager
				.createQuery("select u from SkillVote u where u.status= :skillVoteStatusParam", SkillVote.class)
				.setParameter("skillVoteStatusParam", 1)
				.getResultList();
	}

	

}
