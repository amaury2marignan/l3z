package fr.l3z.repositories;


import fr.l3z.models.SkillProfile;


public interface SkillProfileRepository extends GenericRepository<Long, SkillProfile> {

	boolean isThisSkillIn(Long skillId, SkillProfile skillProfileToCheck);
	int getScore(Long skillId,SkillProfile skillProfileToCheck);
	void setSkillScore(Long skillId,SkillProfile skillProfileToUpdate,int score);
	
	
}