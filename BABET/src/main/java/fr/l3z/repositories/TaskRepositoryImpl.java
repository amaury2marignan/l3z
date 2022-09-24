package fr.l3z.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;

public class TaskRepositoryImpl implements TaskRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public TaskRepositoryImpl() {
		
	}
	
	@Override
	public Task save(Task t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Task find(Long id) {
		return entityManager
				.createQuery("select u from Task u where u.id = :taskIdParam", Task.class)
				.setParameter("taskIdParam", id)
				.getSingleResult();
	}

	@Override
	public Task findByObject(Task t) {
		Task task = entityManager.find(Task.class, t.getId());
		return task;
	}

	@Override
	public List<Task> findAll() {
		return entityManager.createQuery("select u from Task u", Task.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(entityManager.find(Task.class, id));
		
	}

	@Override
	public void deleteByObject(Task t) {
		entityManager.remove(entityManager.find(Task.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, Task t) {
		Task taskAModifier=entityManager.find(Task.class,idAModifier);
		taskAModifier.setName(t.getName());
		taskAModifier.setDescription(t.getDescription());
		taskAModifier.setNextDate(t.getNextDate());
		taskAModifier.setRepeatAfter(t.getRepeatAfter());
		taskAModifier.setSkillProfileMinimumToDo(t.getSkillProfileMinimumToDo());
		taskAModifier.setSkillProfileMinimumToCheck(t.getSkillProfileMinimumToCheck());
		taskAModifier.setStatus(t.getStatus());

		entityManager.merge(taskAModifier);
		
	}

	public boolean compareSkillProfile(SkillProfile userSkillProfile, SkillProfile taskSkillProfile) {
		
		for(SkillNote skillNote : taskSkillProfile.getSkillNoteList()) {
			
		}
		
		return false;
	}

}
