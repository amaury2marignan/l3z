package fr.l3z.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Rule;

public class RuleRepositoryImpl implements RuleRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public RuleRepositoryImpl() {
		
	}
	
	@Override
	public Rule save(Rule t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Rule find(Long id) {
		return entityManager
				.createQuery("select u from Rule u where u.id = :ruleIdParam", Rule.class)
				.setParameter("ruleIdParam", id)
				.getSingleResult();
	}

	@Override
	public Rule findByObject(Rule t) {
		Rule rule = entityManager.find(Rule.class, t.getId());
		return rule;
	}

	@Override
	public List<Rule> findAll() {
		return entityManager.createQuery("select u from Rule u", Rule.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(entityManager.find(Rule.class, id));
		
	}

	@Override
	public void deleteByObject(Rule t) {
		entityManager.remove(entityManager.find(Rule.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, Rule t) {
		Rule ruleAModifier=entityManager.find(Rule.class,idAModifier);
		ruleAModifier.setName(t.getName());
		ruleAModifier.setDescription(t.getDescription());
		ruleAModifier.setTasksList(t.getTasksList());
		ruleAModifier.setSkillProfileMinimumToUpdate(t.getSkillProfileMinimumToUpdate());
		ruleAModifier.setDomain(t.getDomain());

		entityManager.merge(ruleAModifier);
		
	}

	

}
