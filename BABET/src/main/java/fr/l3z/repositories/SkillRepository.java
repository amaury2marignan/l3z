package fr.l3z.repositories;


import java.util.List;

import fr.l3z.models.Event;
import fr.l3z.models.Skill;
import fr.l3z.models.SkillProfile;


public interface SkillRepository extends GenericRepository<Long, Skill> {

	int getNbPoints(SkillProfile skillProfileMinimumToDo);

	List<Skill> findWithFamily(Long id);

	Skill findByName(String string);

	Skill findByNameAndFamily(Long id, String string);
	
	
	

	
	
}