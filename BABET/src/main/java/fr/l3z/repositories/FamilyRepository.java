package fr.l3z.repositories;

import fr.l3z.models.Family;
import fr.l3z.models.User;

public interface FamilyRepository extends GenericRepository<Long, Family> {
	
	Family findByName(String name);
	void setNewUser(Long userId);

	
	
}