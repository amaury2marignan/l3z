package fr.l3z.repositories;


import fr.l3z.models.SkillProfile;
import fr.l3z.models.Task;


public interface TaskRepository extends GenericRepository<Long, Task> {

	boolean compareSkillProfile(SkillProfile skillProfile, SkillProfile skillProfileMinimumToDo);
	
	
}