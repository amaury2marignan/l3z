package fr.l3z.repositories;


import fr.l3z.models.Skill;
import fr.l3z.models.SkillProfile;


public interface SkillRepository extends GenericRepository<Long, Skill> {

	int getNbPoints(SkillProfile skillProfileMinimumToDo);
	
	
	

	
	
}