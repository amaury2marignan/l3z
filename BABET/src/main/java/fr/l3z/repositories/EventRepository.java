package fr.l3z.repositories;


import java.util.List;

import fr.l3z.models.Event;


public interface EventRepository extends GenericRepository<Long, Event> {

	List<Event> findByTask(Long taskId);

	List<Event> findByVote(Long t);

	List<Event> findByFamily(Long id);

	List<Event> findByUser(Long id);

	
	
	
	

	
	
}