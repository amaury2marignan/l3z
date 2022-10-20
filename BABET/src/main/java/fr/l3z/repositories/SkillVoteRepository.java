package fr.l3z.repositories;


import java.util.List;

import fr.l3z.models.SkillVote;



public interface SkillVoteRepository extends GenericRepository<Long, SkillVote> {

	List<SkillVote> findAllToDecide();
	
	
	

	
	
}