package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Family;
import fr.l3z.models.User;

@ApplicationScoped
@Stateless
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Inject
	private FamilyRepository familyRep;
	
	public UserRepositoryImpl() {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("babetdb");
	        this.entityManager = emf.createEntityManager();
	}
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		
	}
	
	@Override
	public User save(User t) {
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
		return t;
	}

	@Override
	public User find(Long id) {
		return entityManager
				.createQuery("select u from User u where u.id = :userIdParam", User.class)
				.setParameter("userIdParam", id)
				.getSingleResult();
	}

	@Override
	public User findByObject(User t) {
		User user = entityManager.find(User.class, t.getId());
		return user;
	}

	@Override
	public List<User> findAll() {
		return entityManager.createQuery("select u from User u", User.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(User.class, id));
		entityManager.getTransaction().commit();
		
	}
	
	

	@Override
	public void deleteByObject(User t) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(User.class, t.getId()));
		entityManager.getTransaction().commit();
	}

	@Override
	public void update(Long idAModifier, User t) {
		entityManager.getTransaction().begin();
		User userAModifier=entityManager.find(User.class,idAModifier);
		userAModifier.setUserName(t.getUserName());
		userAModifier.setPassword(t.getPassword());
		userAModifier.setFamily(t.getFamily());
		userAModifier.setSkillProfile(t.getSkillProfile());
		userAModifier.setScore(t.getScore());
		userAModifier.setColor(t.getColor());
		userAModifier.setIdPicNumber(t.getIdPicNumber());
		userAModifier.setCoins(t.getCoins());
		
		entityManager.merge(userAModifier);
		entityManager.getTransaction().commit();
	}

	@Override
	public User findByUserName(String name) {
		return entityManager
				.createQuery("select u from User u where u.userName = :userNameParam", User.class)
				.setParameter("userNameParam", name)
				.getSingleResult();
	}

	@Override
	public List<User> usersByFamily(Long idFamily) {
		Family f = familyRep.find(idFamily);
		return entityManager
				.createQuery("select u from User u where u.family = :familyParam", User.class)
				.setParameter("familyParam", f)
				.getResultList();
	}

	@Override
	public User findByNameAndFamily(String userName, Long id) {
		return entityManager
				.createQuery("select u from User u where u.userName = :userNameParam AND u.family.id = :familyIdParam", User.class)
				.setParameter("userNameParam", userName)
				.setParameter("familyIdParam", id)
				.getSingleResult();
	}

}
