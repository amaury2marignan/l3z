package fr.l3z.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.l3z.models.Family;
import fr.l3z.models.User;

@ApplicationScoped
@Stateless
public class FamilyRepositoryImpl implements FamilyRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public FamilyRepositoryImpl() {
		
	}
	
	@Override
	public Family save(Family t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public Family find(Long id) {
		return entityManager
				.createQuery("select u from Family u where u.id = :familyIdParam", Family.class)
				.setParameter("familyIdParam", id)
				.getSingleResult();
	}

	@Override
	public Family findByObject(Family t) {
		Family family = entityManager.find(Family.class, t.getId());
		return family;
	}

	@Override
	public List<Family> findAll() {
		return entityManager.createQuery("select u from Family u", Family.class).getResultList();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(entityManager.find(Family.class, id));
		
	}

	@Override
	public void deleteByObject(Family t) {
		entityManager.remove(entityManager.find(Family.class, t.getId()));
		
	}

	@Override
	public void update(Long idAModifier, Family t) {
		Family familyAModifier=entityManager.find(Family.class,idAModifier);
		familyAModifier.setFamilyName(t.getFamilyName());
		familyAModifier.setPassword(t.getPassword());
		entityManager.merge(familyAModifier);
		
	}

	@Override
	public Family findByName(String name) {
		return entityManager
				.createQuery("select u from Family u where u.familyName = :familynameParam", Family.class)
				.setParameter("familynameParam", name)
				.getSingleResult();
	}

	@Override
	public void setNewUser(Long userId) {
		// TODO Auto-generated method stub
		
	}

}
