package fr.l3z.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.SkillNote;
import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;

@ApplicationScoped
@Stateless
public class TaskRepositoryImpl implements TaskRepository {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private SkillProfileRepository skillProfileRep;
	@Inject
	private SkillRepository skillRep;

	public TaskRepositoryImpl() {

	}

	@Override
	public Task save(Task t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Task find(Long id) {
		return entityManager.createQuery("select u from Task u where u.id = :taskIdParam", Task.class)
				.setParameter("taskIdParam", id).getSingleResult();
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
	public List<Task> findByStatus(int status){
		return entityManager.createQuery("select u from Task u where u.status = :taskIdParam", Task.class)
				.setParameter("taskIdParam", status).getResultList();
	}
	
	@Override
	public List<Task> findTasksToDo(Long familyId){
		return entityManager.createQuery("select u from Task u where u.status between '1' and '3'AND u.family.id = :familyIdParam", Task.class)
				.setParameter("familyIdParam", familyId).getResultList();
	}

	@Override
	public void deleteByObject(Task t) {
		entityManager.remove(entityManager.find(Task.class, t.getId()));

	}

	@Override
	public void update(Long idAModifier, Task t) {
		Task taskAModifier = entityManager.find(Task.class, idAModifier);
		taskAModifier.setName(t.getName());
		taskAModifier.setDescription(t.getDescription());
		taskAModifier.setNextDate(t.getNextDate());
		taskAModifier.setRepeatAfter(t.getRepeatAfter());
		taskAModifier.setSkillProfileMinimumToDo(t.getSkillProfileMinimumToDo());
		taskAModifier.setSkillProfileMinimumToCheck(t.getSkillProfileMinimumToCheck());
		taskAModifier.setStatus(t.getStatus());
		taskAModifier.setWhoDidIt(t.getWhoDidIt());
		taskAModifier.setNextTask(t.getNextTask());
		taskAModifier.setNbPoints(t.getNbPoints());
		taskAModifier.setDifficulty(t.getDifficulty());

		entityManager.merge(taskAModifier);

	}

	public boolean compareSkillProfile(SkillProfile userSkillProfile, SkillProfile taskSkillProfile) {
		
		int skillNumber = taskSkillProfile.getSkillNoteList().size();
		

		for (SkillNote taskSkillNote : taskSkillProfile.getSkillNoteList()) {
			
			for (SkillNote userSkillNote : userSkillProfile.getSkillNoteList()) {
				
				if (userSkillNote.getSkill().getName().equals(taskSkillNote.getSkill().getName())) {
				
					if (userSkillNote.getScore() < taskSkillNote.getScore()) {
					
						return false;
					} else {
						skillNumber = skillNumber - 1;
					
					}
				} else {
			
				}
			}
		}
		
		
		
		if (skillNumber == 0) {
			
			return true;
		} else {
		
			return false;
		}

	}

	@Override
	public List<Task> findModels() {
		
		return entityManager.createQuery("select u from Task u where u.status = 0", Task.class)
				.getResultList();
	}

	@Override
	public Task findByName(String nextTaskName) {
		return entityManager.createQuery("select u from Task u where u.name = :taskNameParam AND u.status = 0", Task.class)
				.setParameter("taskNameParam", nextTaskName).getSingleResult();
		
	}

	@Override
	public List<Task> findByStatus0(Long familyId){
		return entityManager.createQuery("select u from Task u where u.status = 0 AND u.family.id = :familyIdParam", Task.class)
				.setParameter("familyIdParam", familyId).getResultList();
	}

	@Override
	public List<Task> findBySkillId(Long id) {
		List<SkillProfile> skListAll = skillProfileRep.findAll();
		List<SkillProfile> skListWith = new ArrayList<SkillProfile>();
		for(SkillProfile sp:skListAll) {
			if (skillProfileRep.isThisSkillIn(id, sp)){
				skListWith.add(sp);
			}
		}
		List<Task> listTaskWith = new ArrayList<Task>();
		List<Task> listTaskAll = findAll();
		for(Task t:listTaskAll) {
			if (skListWith.contains(t.getSkillProfileMinimumToDo())){
				listTaskWith.add(t);
			}
		}
		return listTaskWith;
	}
	
	@Override
	public List<Task> findBySkill0(Long id) {
		List<SkillProfile> skListAll = skillProfileRep.findAll();
		List<SkillProfile> skListWith = new ArrayList<SkillProfile>();
		for(SkillProfile sp:skListAll) {
			if (skillProfileRep.isThisSkillIn(id, sp)){
				skListWith.add(sp);
			}
		}
		List<Task> listTaskWith = new ArrayList<Task>();
		List<Task> listTaskAll = findByStatus(0);
		for(Task t:listTaskAll) {
			if (skListWith.contains(t.getSkillProfileMinimumToDo())){
				listTaskWith.add(t);
			}
		}
		return listTaskWith;
	}

	@Override
	public List<Task> findBySkillToDo(Long id) {
		List<SkillProfile> skListAll = skillProfileRep.findAll();
		List<SkillProfile> skListWith = new ArrayList<SkillProfile>();
		for(SkillProfile sp:skListAll) {
			if (skillProfileRep.isThisSkillIn(id, sp)){
				skListWith.add(sp);
			}
		}
		List<Task> listTaskWith = new ArrayList<Task>();
		List<Task> listTaskAll = findTasksToDo(skillRep.find(id).getFamily().getId());
		for(Task t:listTaskAll) {
			if (skListWith.contains(t.getSkillProfileMinimumToDo())){
				listTaskWith.add(t);
			}
		}
		return listTaskWith;
	}
	

}
