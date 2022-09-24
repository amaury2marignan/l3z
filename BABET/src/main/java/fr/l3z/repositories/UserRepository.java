package fr.l3z.repositories;

import java.util.List;

import fr.l3z.models.User;

public interface UserRepository extends GenericRepository<Long, User> {
	
	User findByUserName(String name);
	List<User> usersByFamily(Long idFamily);

	
	
}