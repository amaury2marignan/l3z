package fr.l3z.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Project;

public class ProjectRepositoryImpl implements ProjectRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public ProjectRepositoryImpl() {
		
	}
	
	@Override
	public Project save(Project t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Project find(Long id) {
		return entityManager
				.createQuery("select u from Project u where u.id = :projectIdParam", Project.class)
				.setParameter("projectIdParam", id)
				.getSingleResult();
	}

	@Override
	public Project findByObject(Project t) {
		Project project = entityManager.find(Project.class, t.getId());
		return project;
	}

	@Override
	public List<Project> findAll() {
		return entityManager.createQuery("select u from Project u", Project.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(entityManager.find(Project.class, id));
		
	}

	@Override
	public void deleteByObject(Project t) {
		entityManager.remove(entityManager.find(Project.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, Project t) {
		Project projectAModifier=entityManager.find(Project.class,idAModifier);
		projectAModifier.setRule(t.getRule());
		projectAModifier.setVote(t.getVote());
		projectAModifier.setOpen(t.getOpen());
		
		entityManager.merge(projectAModifier);
		
	}

	

}
