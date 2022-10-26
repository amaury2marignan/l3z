package fr.l3z.repositories;


import java.util.List;

import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;


public interface TaskRepository extends GenericRepository<Long, Task> {

	boolean compareSkillProfile(SkillProfile skillProfile, SkillProfile skillProfileMinimumToDo);

	List<Task> findModels();

	List<Task> findByStatus(int i);

	List<Task> findTasksToDo(Long familyId);

	Task findByName(String nextTaskName);

	List<Task> findByStatus0(Long id);

	List<Task> findBySkillId(Long id);

	List<Task> findBySkill0(Long id);

	List<Task> findBySkillToDo(Long skillId);
	
	
}