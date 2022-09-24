package fr.l3z.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Domain;


public class DomainRepositoryImpl implements DomainRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public DomainRepositoryImpl() {
		
	}
	
	@Override
	public Domain save(Domain t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Domain find(Long id) {
		return entityManager
				.createQuery("select u from Domain u where u.id = :domainIdParam", Domain.class)
				.setParameter("domainIdParam", id)
				.getSingleResult();
	}

	@Override
	public Domain findByObject(Domain t) {
		Domain domain = entityManager.find(Domain.class, t.getId());
		return domain;
	}

	@Override
	public List<Domain> findAll() {
		return entityManager.createQuery("select u from Domain u", Domain.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(entityManager.find(Domain.class, id));
		
	}

	@Override
	public void deleteByObject(Domain t) {
		entityManager.remove(entityManager.find(Domain.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, Domain t) {
		Domain domainAModifier=entityManager.find(Domain.class,idAModifier);
		domainAModifier.setName(t.getName());
		domainAModifier.setDescription(t.getDescription());
		domainAModifier.setMinimumSkillProfileToUpdate(t.getMinimumSkillProfileToUpdate());
		entityManager.merge(domainAModifier);
		
	}



}
