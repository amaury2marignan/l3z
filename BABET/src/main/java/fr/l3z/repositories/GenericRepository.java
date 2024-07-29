package fr.l3z.repositories;

import java.util.List;

import javax.persistence.EntityManager;

public interface GenericRepository<I, T>  {

	void setEntityManager(EntityManager entityManager);

	T save(T t);

	T find(I id);
	
	T findByObject (T t);
	
	List<T> findAll();
	
	void delete(I id);
	
	void deleteByObject(T t);
	
	void update (I id, T t);

		
	
	
}

