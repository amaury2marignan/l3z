package fr.l3z.repositories;


import java.util.List;

import fr.l3z.models.Event;


public interface EventRepository extends GenericRepository<Long, Event> {

	List<Event> findByTask(Long taskId);

	List<Event> findByVote(Long t);

	List<Event> findByFamily(Long id);

	List<Event> findByUser(Long id);

	List<Event> findByTaskName(String name);

	List<Event> findPurchases(Long id);

	List<Event> findVotes(Long id);

	int pointsOfDay(Long id);

	int pointsOfMonth(Long id);

	int ringsScore(Long id);

	
	
	
	

	
	
}